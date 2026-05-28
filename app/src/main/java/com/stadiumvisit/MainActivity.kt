package com.stadiumvisit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stadiumvisit.data.SessionManager
import com.stadiumvisit.data.StadiumDatabase
import com.stadiumvisit.data.StadiumUserState
import com.stadiumvisit.data.User
import com.stadiumvisit.data.VisitStatus
import com.stadiumvisit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: StadiumDatabase
    private lateinit var sessionManager: SessionManager
    private var map: GoogleMap? = null
    private var selectedStadiumId: String? = null
    private var currentUserId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = StadiumDatabase.getInstance(this)
        sessionManager = SessionManager(this)
        currentUserId = sessionManager.getUserId()
        updateAuthUi()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.registerButton.setOnClickListener { register() }
        binding.loginButton.setOnClickListener { login() }
        binding.logoutButton.setOnClickListener { logout() }

        binding.visitedButton.setOnClickListener { saveStatus(VisitStatus.VISITED) }
        binding.favoriteButton.setOnClickListener { saveStatus(VisitStatus.FAVORITE) }
        binding.wantButton.setOnClickListener { saveStatus(VisitStatus.WANT_TO_VISIT) }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        CoroutineScope(Dispatchers.IO).launch {
            val stadiums = database.stadiumDao().getAllStadiums()
            withContext(Dispatchers.Main) {
                stadiums.forEach { stadium ->
                    val position = LatLng(stadium.latitude, stadium.longitude)
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(position)
                            .title(stadium.name)
                            .snippet(stadium.id)
                    )
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(50.0755, 14.4378), 4.0f))
                googleMap.setOnMarkerClickListener { marker ->
                    selectedStadiumId = marker.snippet
                    binding.selectedStadiumText.text = "Vybráno: ${marker.title}"
                    false
                }
            }
        }
    }

    private fun register() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val displayName = binding.displayNameInput.text.toString().trim()

        if (email.isBlank() || password.isBlank() || displayName.isBlank()) {
            toast("Vyplň email, heslo a jméno")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val exists = database.userDao().findByEmail(email)
            if (exists != null) {
                withContext(Dispatchers.Main) { toast("Účet s tímto emailem už existuje") }
                return@launch
            }
            val userId = database.userDao().insert(User(email = email, password = password, displayName = displayName))
            currentUserId = userId
            sessionManager.setUserId(userId)
            withContext(Dispatchers.Main) {
                updateAuthUi()
                toast("Účet vytvořen")
            }
        }
    }

    private fun login() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        if (email.isBlank() || password.isBlank()) {
            toast("Vyplň email a heslo")
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.userDao().login(email, password)
            withContext(Dispatchers.Main) {
                if (user == null) {
                    toast("Neplatné přihlášení")
                } else {
                    currentUserId = user.id
                    sessionManager.setUserId(user.id)
                    updateAuthUi()
                    toast("Přihlášeno")
                }
            }
        }
    }

    private fun logout() {
        currentUserId = null
        sessionManager.logout()
        updateAuthUi()
        toast("Odhlášeno")
    }

    private fun saveStatus(status: VisitStatus) {
        val userId = currentUserId
        val stadiumId = selectedStadiumId
        if (userId == null) {
            toast("Nejdřív se přihlas")
            return
        }
        if (stadiumId == null) {
            toast("Vyber stadion na mapě")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            database.stadiumUserStateDao().upsert(StadiumUserState(userId = userId, stadiumId = stadiumId, status = status))
            withContext(Dispatchers.Main) { toast("Uloženo: ${status.name}") }
        }
    }

    private fun updateAuthUi() {
        binding.authStatusText.text = if (currentUserId == null) "Nepřihlášen" else "Přihlášen uživatel ID: $currentUserId"
    }

    private fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

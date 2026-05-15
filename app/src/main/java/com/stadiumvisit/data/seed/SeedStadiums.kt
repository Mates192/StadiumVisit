package com.stadiumvisit.data.seed

object SeedStadiums {
    fun insertStatements(): List<String> {
        val rows = listOf(
            row("uk_wembley", "Wembley Stadium", "United Kingdom", "Premier League", "XL", 90000, 51.5560, -0.2796),
            row("es_camp_nou", "Camp Nou", "Spain", "LaLiga", "XL", 99354, 41.3809, 2.1228),
            row("de_allianz", "Allianz Arena", "Germany", "Bundesliga", "L", 75000, 48.2188, 11.6247),
            row("it_san_siro", "San Siro", "Italy", "Serie A", "L", 75817, 45.4781, 9.1240),
            row("fr_parc_des_princes", "Parc des Princes", "France", "Ligue 1", "M", 47929, 48.8414, 2.2530),
            row("nl_johan_cruyff", "Johan Cruyff Arena", "Netherlands", "Eredivisie", "M", 55865, 52.3143, 4.9414),
            row("pt_estadio_luz", "Estádio da Luz", "Portugal", "Primeira Liga", "L", 64642, 38.7528, -9.1848),
            row("cz_epet_arena", "epet ARENA", "Czech Republic", "Chance Liga", "S", 19416, 50.0998, 14.4179)
        )
        return rows.map { stadium ->
            "INSERT INTO stadiums (id, name, country, league, sizeTier, capacity, latitude, longitude) VALUES (${stadium});"
        }
    }

    private fun row(
        id: String,
        name: String,
        country: String,
        league: String,
        sizeTier: String,
        capacity: Int,
        latitude: Double,
        longitude: Double
    ): String {
        return "'${id}','${name}','${country}','${league}','${sizeTier}',${capacity},${latitude},${longitude}"
    }
}

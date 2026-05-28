# Stadium Visit – návrh Android aplikace

## 1) Cíl aplikace
Pomoci fanouškům sportu jednoduše evidovat navštívené stadiony, vizualizovat je na mapě a dlouhodobě je motivovat k dalším návštěvám.

## 2) Doporučený základ místo Google Earth
Google Earth je skvělý pro inspiraci, ale pro mobilní produkt je praktičtější kombinace:

- **Google Maps SDK for Android** (nebo Mapbox) pro mapové UI.
- **Google Places API / OpenStreetMap data** pro vyhledání stadionů.
- **Room (lokální DB)** pro první verzi offline knihovny.

Důvod: rychlejší vývoj, lepší práce s body zájmu, jednodušší UX na mobilu a lepší kontrola nad daty uživatele.

## 3) MVP (první verze)
Nejmenší užitečná verze by měla umět:

1. Přihlášení volitelně (nebo i bez účtu lokálně).
2. Vyhledat stadion podle názvu nebo kliknutím na mapu.
3. Označit stadion jako **navštívený**.
4. Uložit stadion do **Moje knihovna**.
5. Filtrovat seznam na „vše / navštívené / nenavštívené".
6. Detail stadionu: název, sport, město, datum návštěvy, poznámka.

## 4) Datový model (jednoduchý)

### Stadion
- `id`
- `name`
- `sportType` (football, hockey, athletics, ...)
- `league` (volitelné)
- `country`
- `city`
- `latitude`
- `longitude`

### Návštěva
- `id`
- `stadiumId`
- `visitedAt`
- `note`
- `rating` (volitelné)
- `photoUri` (později)

## 5) Motivace a gamifikace (fáze 2)
Inspirace stylem ČSFD/ČBDB, ale pro stadiony:

- **Odznaky / achievementy**
  - „Fotbalový fanda I“ = 10 fotbalových stadionů
  - „Groundhopper CZ“ = 20 stadionů v ČR
  - „Prvoligový sběratel“ = návštěva všech stadionů 1. ligy
- **Série návštěv**: 3 měsíce po sobě alespoň 1 nový stadion.
- **Progres kolekce**: např. 12/16 stadionů dané soutěže.
- **Osobní statistiky**: počet stadionů, zemí, sportů, měst.

## 6) Dlouhodobý roadmap

### Fáze A – Core
- Mapy, vyhledávání, označení navštívených, knihovna.

### Fáze B – Community light
- Sdílení profilu (jen veřejné statistiky).
- Žebříčky mezi přáteli.

### Fáze C – Pokročilé funkce
- Import/export dat (CSV/JSON).
- Doporučování stadionů „blízko mě“.
- AI tipy na výjezdy (vzdálenost, soutěž, oblíbené týmy).

## 7) Technický návrh stacku (Android)
- **Kotlin + Jetpack Compose**
- **Architecture**: MVVM + Repository
- **DB**: Room
- **Mapy**: Google Maps SDK (alternativně Mapbox)
- **DI**: Hilt
- **Asynchronně**: Kotlin Coroutines + Flow
- **Backend později**: Firebase nebo Supabase

## 8) Jak začít prakticky (2–3 týdny)

### Týden 1
- UI skeleton (mapa + seznam + detail)
- Room modely `Stadium` a `Visit`
- Přidání stadionu ručně + označení navštívení

### Týden 2
- Vyhledávání stadionů (API)
- Filtrování a základní statistiky
- Persistované nastavení a základní onboarding

### Týden 3
- Achievement engine v jednoduché podobě
- Export dat
- Uzavřená beta s pár uživateli

## 9) Největší rizika a jak je ošetřit
- **Kvalita dat stadionů** → začít ručně kurátorovanou sadou + později API.
- **Složitost map** → držet MVP co nejjednodušší, bez 3D prvků.
- **Motivace uživatele** → od začátku přidat malé milestone odznaky.

## 10) Doporučení
Začni s mapovým základem (Google Maps/Mapbox), ne přímo s Google Earth. Earth je výborný pro vizuál, ale pro každodenní mobilní používání a rychlý vývoj MVP je klasická mapová vrstva + kvalitní lokální model dat praktičtější.

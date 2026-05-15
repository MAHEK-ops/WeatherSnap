# WeatherSnap 

A production-quality Android app that lets users search live weather for any city, capture a photo using a custom CameraX camera, compress the image, add field notes, and save weather reports locally - all without requiring an API key.

---

## Screenshots

| Weather Screen | Create Report | Custom Camera | Saved Reports |
|---|---|---|---|
| _(add screenshot)_ | _(add screenshot)_ | _(add screenshot)_ | _(add screenshot)_ |

---

## Features

- **City search with autocomplete** - suggestions appear after 2 characters, cached in-memory to avoid redundant API calls
- 🌡️ **Live weather data** - temperature, condition, humidity, wind speed, pressure via Open-Meteo (no API key needed)
- **Custom CameraX camera** - fully built from scratch, no device camera intent used
- **Image compression** - original and compressed file sizes displayed on every report
- **Field notes** - free-text notes attached to each report
- **Local persistence** - all reports saved to Room database, survive app restarts
- **Draft recovery** - in-progress reports survive screen rotation and app backgrounding without data loss or duplicate saves
- **Saved reports list** - view all reports with image, weather snapshot, notes, sizes, and timestamp
- **Polished dark UI** - Material 3 dark theme with olive accent, animated states, smooth navigation transitions

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository pattern |
| DI | Hilt |
| Navigation | Navigation Compose |
| Networking | Retrofit + OkHttp + Gson |
| Local DB | Room |
| Camera | CameraX |
| Image loading | Coil |
| Async | Coroutines + StateFlow |
| Build | Gradle KTS + Version Catalog |

---

## Architecture

```
UI Layer (Compose Screens)
    ↓  observes StateFlow
ViewModel Layer
    ↓  calls suspend functions
Repository Layer
    ↓
Data Sources:
    ├── WeatherRemoteDataSource  →  Open-Meteo REST API (Retrofit)
    ├── ReportLocalDataSource    →  Room Database (DAO)
    └── ImageRepository          →  CameraX capture + Bitmap compression
```

### Package Structure

```
com.weathersnap/
├── di/                        # Hilt modules (network, database, app)
├── data/
│   ├── local/
│   │   ├── dao/               # ReportDao, DraftReportDao
│   │   └── entity/            # ReportEntity, DraftReportEntity
│   ├── remote/
│   │   ├── api/               # GeocodingApi, WeatherApi (Retrofit interfaces)
│   │   └── model/             # API response data classes
│   └── repository/            # WeatherRepository, ReportRepository (impl)
├── domain/
│   └── model/                 # CityResult, WeatherData, WeatherReport (domain models)
├── ui/
│   ├── theme/                 # Color, Type, Theme (dark olive palette)
│   ├── navigation/            # AppNavigation (NavHost + routes)
│   ├── weather/               # WeatherScreen + WeatherViewModel
│   ├── createreport/          # CreateReportScreen + CreateReportViewModel
│   ├── camera/                # CameraScreen + CameraViewModel
│   └── reports/               # SavedReportsScreen + SavedReportsViewModel
├── util/                      # ImageCompressor, FileUtils, Extensions
└── MainActivity.kt
```

---

## API

Uses [Open-Meteo](https://open-meteo.com/) - **no API key required**.

| Purpose | Endpoint |
|---|---|
| City autocomplete | `https://geocoding-api.open-meteo.com/v1/search?name={query}` |
| Current weather | `https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current_weather=true&hourly=relativehumidity_2m,surface_pressure` |

Weather fields shown: temperature, condition (WMO weather code), humidity, wind speed, pressure.

---

## Developer Judgment Challenge - Draft Recovery

### Problem
If a user selects weather, opens Create Report, captures a photo, enters notes, and then rotates the device or backgrounds the app before saving - the in-progress report must be fully recoverable with no duplicate saves.

### Solution: Two-Layer Draft Recovery

**Layer 1 - `SavedStateHandle` (handles rotation)**

`CreateReportViewModel` stores all draft fields (weather JSON, image path, notes, file sizes) inside `SavedStateHandle`. Android automatically saves and restores this across configuration changes (rotation) without any extra code.

**Layer 2 - Room `DraftReport` table (handles process death)**

A single-row Room table (always `id = 1`) acts as a write-ahead staging area. When the user captures a photo or enters notes, the draft is written to Room on the IO dispatcher. On navigating back to Create Report, the ViewModel checks for an existing draft row and silently restores it.

**Draft lifecycle:**
- **Created:** when user taps "Create Report" - weather snapshot is locked and written immediately
- **Updated:** when photo is captured or notes change (debounced 500ms write to avoid thrashing)
- **Deleted:** atomically when "Save Report" succeeds, in the same coroutine scope as the report insert

**Duplicate prevention:**
The Save button is disabled while saving is in progress via `isSaving: Boolean` in UI state. The draft is deleted in the same transaction as the report insert - there is no window where both exist simultaneously.

**Temporary file cleanup:**
- CameraX captures to `cacheDir/temp_capture_<uuid>.jpg`
- After compression, the compressed file is written to `filesDir/reports/<uuid>.jpg`
- The raw temp file is deleted immediately after compression succeeds
- If the user discards (navigates back without saving), `ViewModel.onCleared()` deletes the compressed file and clears the draft row

**Weather snapshot immutability:**
The exact `WeatherData` object selected at report creation time is serialized to JSON and stored in the draft. It is never re-fetched or replaced - even if the user backgrounds the app for hours, the saved report contains the exact conditions that were displayed when the user tapped "Create Report".

### Tradeoffs

| Decision | Reason |
|---|---|
| Room draft over in-memory only | Survives process death, not just rotation |
| Single-row draft (id=1) | Enforces one in-progress report at a time - matches the app's UX flow |
| SavedStateHandle + Room together | SavedStateHandle is instant (no IO); Room covers the rare process-death case |
| 500ms debounce on Room writes | Avoids thrashing the database on every keystroke in the notes field |
| Weather snapshot locked at navigation time | Guarantees report integrity - re-fetching could silently change conditions |

---

## Setup & Run

### Prerequisites
- Android Studio Hedgehog or newer (tested on Panda 2025.3.4)
- Android SDK API 26+
- A device or emulator with a camera (for CameraX)

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/WeatherSnap.git

# 2. Open in Android Studio
# File → Open → select the WeatherSnap folder

# 3. Wait for Gradle sync to complete

# 4. Run on device or emulator
# Click the green Run button or press Shift+F10
```

No API keys needed. No `.env` file. Just clone and run.

### Camera on Emulator
To test CameraX on an emulator, make sure the emulator has a **virtual camera** enabled:
- AVD Manager → Edit → Show Advanced Settings → Camera → Front/Back → select "Emulated" or "Webcam"

---

## Screen Recording Flow

The screen recording demonstrates:
1. City autocomplete - type 3+ letters, see animated suggestion list
2. Select city - weather loads with animated state transition
3. Tap "Create Report" - weather snapshot locked
4. Custom camera screen - live CameraX preview
5. Capture photo - image compression runs, original vs compressed sizes shown
6. Enter field notes
7. Save report - navigates to saved reports list
8. Saved reports - image, weather details, sizes, timestamp all visible
9. Rotate device on Create Report screen - draft fully recovered, no data loss

---

## Bonus Features Implemented

- [x] Debug-only OkHttp network logging interceptor (`BuildConfig.DEBUG` guard)
- [x] In-memory city suggestion cache (avoids repeated API calls for same query)
- [x] Animated city suggestion list (`AnimatedVisibility` slide + fade)
- [x] Animated weather state transitions (`Crossfade` between Loading / Success / Error / Empty)
- [x] Animated image preview after capture (`animateContentSize`)
- [x] IO dispatcher enforced on all Room and file operations
- [ ] Unit tests for ViewModel/Repository _(planned)_
- [ ] Compose UI tests _(planned)_

---

## Design Decisions

**Why Open-Meteo?**
Free, no API key, reliable, and the assignment explicitly requires it.

**Why Hilt over manual DI?**
Hilt eliminates boilerplate, integrates seamlessly with ViewModels via `@HiltViewModel`, and is the Google-recommended DI solution for Android.

**Why Coil for image loading?**
Coil is Kotlin-first, Compose-native, handles caching and file URIs out of the box, and is significantly lighter than Glide or Picasso in a Compose project.

**Why a single-row Draft table instead of saving to SharedPreferences?**
Room gives us type safety, coroutine support, and the ability to query/delete atomically. SharedPreferences would require manual JSON serialization without any of those benefits.

**Why `screenOrientation="portrait"` in the Manifest?**
CameraX `PreviewView` requires careful lifecycle handling in landscape. Locking to portrait keeps the camera implementation clean and reliable. The draft recovery system still handles the `SavedStateHandle` restoration path for completeness.

---

## Author

Built as part of an Android internship assignment.  
Stack: Kotlin · Jetpack Compose · MVVM · Hilt · Room · CameraX · Retrofit · Material 3
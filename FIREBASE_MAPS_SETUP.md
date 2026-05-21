# Firebase and Google Maps Setup

This project already has the code changes for Firebase Firestore and Google Maps on the Store Locator screen.

## 1. Firebase

1. Open the Firebase console: https://console.firebase.google.com/
2. Create a project or open your existing project.
3. Add an Android app with this package name:

```text
com.example.janaushadhifinder
```

4. Download the Firebase config file and place it here:

```text
app/google-services.json
```

5. In Firebase, enable Firestore Database.

Create this collection:

```text
stores
```

Each document should use these fields:

```json
{
  "name": "PMBJP Kendra Andheri",
  "address": "Near Andheri Station, Mumbai",
  "distanceKm": 2.4,
  "isOpen": true,
  "phone": "+91 90000 10001",
  "latitude": 19.1197,
  "longitude": 72.8464
}
```

## 2. Google Maps

1. Open Google Cloud Console: https://console.cloud.google.com/
2. Enable **Maps SDK for Android**.
3. Create an API key.
4. Open `local.properties` and replace:

```properties
MAPS_API_KEY=PASTE_YOUR_GOOGLE_MAPS_API_KEY_HERE
```

with your real key.

## 3. Files Changed

Firebase and Google Maps dependencies:

```text
build.gradle.kts
app/build.gradle.kts
```

Maps API key and location permissions:

```text
app/src/main/AndroidManifest.xml
local.properties
```

Store data model and demo coordinates:

```text
app/src/main/java/com/example/janaushadhifinder/data/JanAushadhiStore.kt
app/src/main/java/com/example/janaushadhifinder/data/SampleData.kt
```

Real map, Firestore loading, and Google Maps directions button:

```text
app/src/main/java/com/example/janaushadhifinder/ui/screens/StoreLocatorScreen.kt
```

## 4. Important Notes

The app uses sample stores until `app/google-services.json` is added.

After adding `google-services.json`, reopen or sync the project in Android Studio.

If the map is blank, check that:

1. `MAPS_API_KEY` is correct.
2. Maps SDK for Android is enabled.
3. The key is allowed for this package name: `com.example.janaushadhifinder`.

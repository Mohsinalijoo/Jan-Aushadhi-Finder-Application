# Medicine Database Setup

The app now reads medicines from this Firestore collection:

```text
medicines
```

Until Firebase is configured, the app still shows the sample medicines from `SampleData.kt`.

## Firestore Document Format

Each document in `medicines` should have these fields:

```json
{
  "brandName": "Dolo 650",
  "genericName": "Paracetamol",
  "salt": "Paracetamol 650mg",
  "brandPrice": 35,
  "genericPrice": 8
}
```

## Best Way to Add 500+ Medicines

Do not add 500 medicines manually in Firebase Console.

Use a CSV file and import it with the script in:

```text
tools/import_medicines
```

CSV columns must be:

```text
brandName,genericName,salt,brandPrice,genericPrice
```

Example:

```csv
brandName,genericName,salt,brandPrice,genericPrice
Dolo 650,Paracetamol,Paracetamol 650mg,35,8
Pan 40,Pantoprazole,Pantoprazole 40mg,145,25
```

Use verified medicine data from an official or trusted source. Do not use random generated data for a medical app.

## Import Steps

1. Open Firebase Console.
2. Go to **Project Settings > Service accounts**.
3. Click **Generate new private key**.
4. Save the downloaded file as:

```text
tools/import_medicines/serviceAccountKey.json
```

5. Put your 500+ medicine rows into:

```text
tools/import_medicines/medicines.csv
```

6. Open Terminal in this folder:

```text
tools/import_medicines
```

7. Run:

```bash
npm install
npm run import
```

After import, open Firebase Console and check:

```text
Firestore Database > medicines
```

## Files Changed in App

Firestore medicine loading:

```text
app/src/main/java/com/example/janaushadhifinder/ui/screens/MedicineSearchScreen.kt
```

Firestore-compatible medicine model:

```text
app/src/main/java/com/example/janaushadhifinder/data/Medicine.kt
```

const fs = require("fs");
const path = require("path");
const admin = require("firebase-admin");

const csvPath = path.resolve(process.argv[2] || "medicines.csv");
const serviceAccountPath = path.resolve("serviceAccountKey.json");

if (!fs.existsSync(serviceAccountPath)) {
  console.error("Missing serviceAccountKey.json in tools/import_medicines.");
  console.error("Download it from Firebase Project Settings > Service accounts.");
  process.exit(1);
}

if (!fs.existsSync(csvPath)) {
  console.error(`Missing CSV file: ${csvPath}`);
  process.exit(1);
}

admin.initializeApp({
  credential: admin.credential.cert(require(serviceAccountPath)),
});

const db = admin.firestore();

function parseCsv(text) {
  const rows = [];
  let row = [];
  let value = "";
  let inQuotes = false;

  for (let i = 0; i < text.length; i += 1) {
    const char = text[i];
    const next = text[i + 1];

    if (char === '"' && inQuotes && next === '"') {
      value += '"';
      i += 1;
    } else if (char === '"') {
      inQuotes = !inQuotes;
    } else if (char === "," && !inQuotes) {
      row.push(value.trim());
      value = "";
    } else if ((char === "\n" || char === "\r") && !inQuotes) {
      if (char === "\r" && next === "\n") i += 1;
      row.push(value.trim());
      if (row.some((cell) => cell.length > 0)) rows.push(row);
      row = [];
      value = "";
    } else {
      value += char;
    }
  }

  if (value.length > 0 || row.length > 0) {
    row.push(value.trim());
    if (row.some((cell) => cell.length > 0)) rows.push(row);
  }

  return rows;
}

function slugify(value) {
  return value
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, "-")
    .replace(/^-+|-+$/g, "")
    .slice(0, 140);
}

async function importMedicines() {
  const rows = parseCsv(fs.readFileSync(csvPath, "utf8"));
  const headers = rows.shift();

  if (!headers) {
    throw new Error("CSV is empty.");
  }

  const index = Object.fromEntries(headers.map((header, i) => [header, i]));
  const required = ["brandName", "genericName", "salt", "brandPrice", "genericPrice"];
  const missing = required.filter((field) => !(field in index));

  if (missing.length > 0) {
    throw new Error(`CSV is missing required columns: ${missing.join(", ")}`);
  }

  let batch = db.batch();
  let pendingWrites = 0;
  let imported = 0;

  for (const row of rows) {
    const medicine = {
      brandName: row[index.brandName],
      genericName: row[index.genericName],
      salt: row[index.salt],
      brandPrice: Number(row[index.brandPrice]),
      genericPrice: Number(row[index.genericPrice]),
    };

    if (!medicine.brandName || !medicine.genericName || !medicine.salt) continue;
    if (!Number.isFinite(medicine.brandPrice) || !Number.isFinite(medicine.genericPrice)) continue;

    const docId = slugify(`${medicine.brandName}-${medicine.salt}`) || db.collection("medicines").doc().id;
    const docRef = db.collection("medicines").doc(docId);

    batch.set(docRef, medicine, { merge: true });
    pendingWrites += 1;
    imported += 1;

    if (pendingWrites >= 450) {
      await batch.commit();
      batch = db.batch();
      pendingWrites = 0;
    }
  }

  if (pendingWrites > 0) {
    await batch.commit();
  }

  console.log(`Imported ${imported} medicines into Firestore collection: medicines`);
}

importMedicines().catch((error) => {
  console.error(error);
  process.exit(1);
});

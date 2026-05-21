package com.example.janaushadhifinder.data

object SampleData {
    private val realSamples = listOf(
        Medicine("Augmentin 625", "Amoxicillin + Clavulanic Acid", "Amoxicillin 500mg + Clavulanic Acid 125mg", 210, 60),
        Medicine("Pan 40", "Pantoprazole", "Pantoprazole 40mg", 145, 25),
        Medicine("Telma 40", "Telmisartan", "Telmisartan 40mg", 110, 20),
        Medicine("Dolo 650", "Paracetamol", "Paracetamol 650mg", 35, 8),
        Medicine("Azee 500", "Azithromycin", "Azithromycin 500mg", 120, 35),
        Medicine("Crestor 10", "Rosuvastatin", "Rosuvastatin 10mg", 250, 55),
        Medicine("Shelcal 500", "Calcium + Vitamin D3", "Calcium Carbonate + Vitamin D3", 160, 45),
        Medicine("Montair LC", "Montelukast + Levocetirizine", "Montelukast 10mg + Levocetirizine 5mg", 180, 42),
        Medicine("Glycomet 500", "Metformin", "Metformin 500mg", 70, 12),
        Medicine("Ecosprin 75", "Aspirin", "Aspirin 75mg", 45, 10),
        Medicine("Atorva 10", "Atorvastatin", "Atorvastatin 10mg", 140, 28),
        Medicine("Thyronorm 50", "Levothyroxine", "Levothyroxine 50mcg", 150, 30)
    )

    private val generatedDemoMedicines = List(520) { index ->
        val source = realSamples[index % realSamples.size]
        val pack = (index % 5) + 1
        source.copy(
            brandName = "${source.brandName} Demo $pack",
            brandPrice = source.brandPrice + (index % 40),
            genericPrice = source.genericPrice + (index % 8)
        )
    }

    val medicines: List<Medicine> = realSamples + generatedDemoMedicines

    val popularSwitches: List<Medicine> = realSamples.take(3)

    val stores = listOf(
        JanAushadhiStore(
            name = "PMBJP Kendra Andheri",
            address = "Near Andheri Station, Mumbai",
            distanceKm = 2.4,
            isOpen = true,
            phone = "+91 90000 10001",
            latitude = 19.1197,
            longitude = 72.8464
        ),
        JanAushadhiStore(
            name = "Jan-Aushadhi Kendra Bandra",
            address = "Bandra West, Mumbai",
            distanceKm = 5.8,
            isOpen = true,
            phone = "+91 90000 10002",
            latitude = 19.0596,
            longitude = 72.8295
        ),
        JanAushadhiStore(
            name = "Generic Medicine Store Kurla",
            address = "Kurla Market Road, Mumbai",
            distanceKm = 8.1,
            isOpen = false,
            phone = "+91 90000 10003",
            latitude = 19.0726,
            longitude = 72.8845
        ),
        JanAushadhiStore(
            name = "PMBJP Kendra Ghatkopar",
            address = "MG Road, Ghatkopar East",
            distanceKm = 9.6,
            isOpen = true,
            phone = "+91 90000 10004",
            latitude = 19.0865,
            longitude = 72.9089
        )
    )
}

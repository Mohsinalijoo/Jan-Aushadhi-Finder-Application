package com.example.janaushadhifinder.data

data class Medicine(
    val brandName: String = "",
    val genericName: String = "",
    val salt: String = "",
    val brandPrice: Int = 0,
    val genericPrice: Int = 0
) {
    val savings: Int
        get() = brandPrice - genericPrice

    val savingsPercent: Int
        get() = if (brandPrice == 0) 0 else (savings * 100) / brandPrice
}

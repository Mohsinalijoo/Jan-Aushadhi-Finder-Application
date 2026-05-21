package com.example.janaushadhifinder.utils

import com.example.janaushadhifinder.data.Medicine
import kotlin.math.min

object FuzzySearch {
    fun search(query: String, medicines: List<Medicine>): List<Medicine> {
        val cleanQuery = query.normalize()
        if (cleanQuery.isBlank()) return medicines.take(20)

        return medicines
            .asSequence()
            .map { medicine -> medicine to scoreMedicine(cleanQuery, medicine) }
            .filter { (_, score) -> score <= 4 }
            .sortedBy { (_, score) -> score }
            .map { (medicine, _) -> medicine }
            .take(50)
            .toList()
    }

    private fun scoreMedicine(query: String, medicine: Medicine): Int {
        val fields = listOf(medicine.brandName, medicine.genericName, medicine.salt)
            .map { it.normalize() }

        if (fields.any { it.contains(query) }) return 0

        return fields.minOf { field ->
            val tokenScore = field.split(" ")
                .filter { it.isNotBlank() }
                .minOf { token -> levenshtein(query, token) }
            min(tokenScore, levenshtein(query, field))
        }
    }

    private fun String.normalize(): String {
        return lowercase().trim().replace(Regex("[^a-z0-9 ]"), " ")
    }

    private fun levenshtein(a: String, b: String): Int {
        if (a == b) return 0
        if (a.isEmpty()) return b.length
        if (b.isEmpty()) return a.length

        val previous = IntArray(b.length + 1) { it }
        val current = IntArray(b.length + 1)

        for (i in 1..a.length) {
            current[0] = i
            for (j in 1..b.length) {
                val cost = if (a[i - 1] == b[j - 1]) 0 else 1
                current[j] = minOf(
                    current[j - 1] + 1,
                    previous[j] + 1,
                    previous[j - 1] + cost
                )
            }
            for (j in previous.indices) {
                previous[j] = current[j]
            }
        }

        return previous[b.length]
    }
}

package com.example.exercise2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WordCollection(private val words: List<String>) {

    // Step 1: associateWith — creates a Map<String, Int> (word -> length)
    // Uses a lambda: (String) -> Int
    fun toWordLengthMap(): Map<String, Int> {
        return words.associateWith { it.length }    // lambda: word -> its length
    }

    // Step 2: filter map entries where length > 4
    // Uses a lambda predicate on map entries
    fun filterByLength(map: Map<String, Int>, minLength: Int): Map<String, Int> {
        return map.filter { (_, length) -> length > minLength }  // lambda: filter HOF
    }

    // Extra: map just the lengths
    fun getLengths(): List<Int> {
        return words.map { it.length }              // map HOF + lambda
    }

    // Extra: fold — total characters across all words
    fun totalCharacters(): Int {
        return words.fold(0) { acc, word -> acc + word.length } // fold HOF + lambda
    }

    // Extra: filter words by minimum length directly
    fun wordsLongerThan(min: Int): List<String> {
        return words.filter { it.length > min }     // filter HOF + lambda
    }

    override fun toString(): String = words.toString()
}

// ── Menu & Entry Point ────────────────────────────────────────
fun main() {
    val words = WordCollection(listOf("apple", "cat", "banana", "dog", "elephant"))
    var running = true

    while (running) {
        println("\n==========================================")
        println("  Exercise 2: Collection Transformations  ")
        println("==========================================")
        println("  Words: [apple, cat, banana, dog, elephant]")
        println("------------------------------------------")
        println("  1. Show word → length map (associateWith)")
        println("  2. Filter words with length > 4 (Exercise answer)")
        println("  3. Show only word lengths (map)")
        println("  4. Total characters across all words (fold)")
        println("  5. Words longer than 3 characters (filter)")
        println("------------------------------------------")
        println("  0. Exit")
        println("==========================================")
        print("  Enter your choice: ")

        val choice = readLine()?.trim()
        println()

        when (choice) {
            "1" -> {
                val map = words.toWordLengthMap()       // associateWith lambda
                println("  [MAP] Word → Length map:")
                map.forEach { (word, length) ->         // forEach lambda
                    println("  $word → $length")
                }
            }
            "2" -> {
                // This is the exact exercise solution
                val map = words.toWordLengthMap()       // associateWith lambda
                val filtered = words.filterByLength(map, 4) // filter lambda
                println("  [FILTER] Words with length > 4:")
                filtered.forEach { (word, length) ->    // forEach lambda
                    println("  $word has length $length")
                }
            }
            "3" -> {
                val lengths = words.getLengths()        // map lambda
                println("  [MAP] Word lengths:")
                println("  $lengths")
            }
            "4" -> {
                val total = words.totalCharacters()     // fold lambda
                println("  [FOLD] Total characters across all words:")
                println("  $total")
            }
            "5" -> {
                val result = words.wordsLongerThan(3)  // filter lambda
                println("  [FILTER] Words longer than 3 characters:")
                println("  $result")
            }
            "0" -> {
                println("  Goodbye!")
                running = false
            }
            else -> println("  Invalid choice. Please enter a number from 0 to 5.")
        }
    }
}

package com.example.exercise2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WordCollection(private val words: List<String>) {


    fun toWordLengthMap(): Map<String, Int> {
        return words.associateWith { it.length }   
    }

   
    fun filterByLength(map: Map<String, Int>, minLength: Int): Map<String, Int> {
        return map.filter { (_, length) -> length > minLength }  
    }

    
    fun getLengths(): List<Int> {
        return words.map { it.length }              
s
    fun totalCharacters(): Int {
        return words.fold(0) { acc, word -> acc + word.length } 
    }

    y
    fun wordsLongerThan(min: Int): List<String> {
        return words.filter { it.length > min }     
    }

    override fun toString(): String = words.toString()
}


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
                val map = words.toWordLengthMap()       
                println("  [MAP] Word → Length map:")
                map.forEach { (word, length) ->         
                    println("  $word → $length")
                }
            }
            "2" -> {
                
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

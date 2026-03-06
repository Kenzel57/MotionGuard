package com.example.exercise1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NumberList(private val numbers: List<Int>) {

    // processList — Exercise 1 function
    fun processList(predicate: (Int) -> Boolean): List<Int> {
        return numbers.filter(predicate)            // filter HOF + lambda
    }

    // mapList — transforms each element
    fun mapList(transform: (Int) -> Int): List<Int> {
        return numbers.map(transform)               // map HOF + lambda
    }

    // foldList — reduces list to a single value
    fun foldList(initial: Int, accumulator: (Int, Int) -> Int): Int {
        return numbers.fold(initial, accumulator)   // fold HOF + lambda
    }

    override fun toString(): String = numbers.toString()
}

// ── Menu & Entry Point ────────────────────────────────────────
fun main() {
    val nums = NumberList(listOf(1, 2, 3, 4, 5, 6))
    var running = true

    while (running) {
        println("\n==========================================")
        println("  Higher-Order Functions — Choose Option  ")
        println("==========================================")
        println("  Original list: [1, 2, 3, 4, 5, 6]")
        println("------------------------------------------")
        println("  FILTER (processList)")
        println("  1. Even numbers")
        println("  2. Odd numbers")
        println("  3. Numbers greater than 3")
        println("  4. Numbers less than 4")
        println("------------------------------------------")
        println("  MAP")
        println("  5. Double each number")
        println("  6. Square each number")
        println("  7. Add 10 to each number")
        println("------------------------------------------")
        println("  FOLD")
        println("  8. Sum of all numbers")
        println("  9. Product of all numbers")
        println("------------------------------------------")
        println("  0. Exit")
        println("==========================================")
        print("  Enter your choice: ")

        val choice = readLine()?.trim()

        println()
        when (choice) {
            "1" -> {
                val result = nums.processList { it % 2 == 0 }       // lambda: even
                println("  [FILTER] Even numbers")
                println("  Result: $result")
            }
            "2" -> {
                val result = nums.processList { it % 2 != 0 }       // lambda: odd
                println("  [FILTER] Odd numbers")
                println("  Result: $result")
            }
            "3" -> {
                val result = nums.processList { it > 3 }            // lambda: > 3
                println("  [FILTER] Numbers greater than 3")
                println("  Result: $result")
            }
            "4" -> {
                val result = nums.processList { it < 4 }            // lambda: < 4
                println("  [FILTER] Numbers less than 4")
                println("  Result: $result")
            }
            "5" -> {
                val result = nums.mapList { it * 2 }                // lambda: double
                println("  [MAP] Double each number")
                println("  Result: $result")
            }
            "6" -> {
                val result = nums.mapList { it * it }               // lambda: square
                println("  [MAP] Square each number")
                println("  Result: $result")
            }
            "7" -> {
                val result = nums.mapList { it + 10 }               // lambda: +10
                println("  [MAP] Add 10 to each number")
                println("  Result: $result")
            }
            "8" -> {
                val result = nums.foldList(0) { acc, n -> acc + n } // lambda: sum
                println("  [FOLD] Sum of all numbers")
                println("  Result: $result")
            }
            "9" -> {
                val result = nums.foldList(1) { acc, n -> acc * n } // lambda: product
                println("  [FOLD] Product of all numbers")
                println("  Result: $result")
            }
            "0" -> {
                println("  Goodbye!")
                running = false
            }
            else -> println("  Invalid choice. Please enter a number from 0 to 9.")
        }
    }
}

package com.example.exercise3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Person(val name: String, val age: Int)

// ── OOP: Class encapsulating the people list ──────────────────
class PeopleCollection(private val people: List<Person>) {

    // Step 1 — filter by name starting with 'A' or 'B'
    // Uses a lambda predicate: (Person) -> Boolean
    fun filterByNameStarting(vararg letters: Char): List<Person> {
        return people.filter { it.name.first() in letters }  // filter HOF + lambda
    }

    // Step 2 — extract ages from a list of people
    // Uses a lambda transform: (Person) -> Int
    fun extractAges(persons: List<Person>): List<Int> {
        return persons.map { it.age }                        // map HOF + lambda
    }

    // Step 3 — calculate average using fold
    // Uses a lambda accumulator: (Int, Int) -> Int
    fun calculateAverage(ages: List<Int>): Double {
        if (ages.isEmpty()) return 0.0
        val sum = ages.fold(0) { acc, age -> acc + age }     // fold HOF + lambda
        return sum.toDouble() / ages.size
    }

    // Extra: filter by minimum age
    fun filterByMinAge(minAge: Int): List<Person> {
        return people.filter { it.age >= minAge }            // filter HOF + lambda
    }

    // Extra: get names only
    fun getNames(): List<String> {
        return people.map { it.name }                        // map HOF + lambda
    }

    // Extra: oldest person using fold
    fun findOldest(): Person? {
        return people.fold(people.first()) { oldest, person ->  // fold HOF + lambda
            if (person.age > oldest.age) person else oldest
        }
    }

    // Extra: total age of everyone using fold
    fun totalAge(): Int {
        return people.fold(0) { acc, person -> acc + person.age } // fold HOF + lambda
    }

    override fun toString(): String = people.toString()
}

// ── Menu & Entry Point ────────────────────────────────────────
fun main() {
    val people = PeopleCollection(
        listOf(
            Person("Alice", 25),
            Person("Kenzel", 30),
            Person("Jo", 35),
            Person("Ryan", 22),
            Person("Ben", 28)
        )
    )

    var running = true

    while (running) {
        println("\n==========================================")
        println("   Exercise 3: Complex Data Processing    ")
        println("==========================================")
        println("  People: Alice(25), Bob(30), Charlie(35),")
        println("          Anna(22), Ben(28)")
        println("------------------------------------------")
        println("  1. Average age of names starting with A or B")
        println("     (Exercise answer — all 4 steps)")
        println("------------------------------------------")
        println("  2. Filter people whose name starts with A or B")
        println("  3. Extract ages only (map)")
        println("  4. Total age of all people (fold)")
        println("  5. Find the oldest person (fold)")
        println("  6. Filter people aged 25 and above")
        println("  7. Show all names (map)")
        println("------------------------------------------")
        println("  0. Exit")
        println("==========================================")
        print("  Enter your choice: ")

        val choice = readLine()?.trim()
        println()

        when (choice) {
            "1" -> {
                // ── Full Exercise 3 solution ──────────────
                // Step 1: filter
                val filtered = people.filterByNameStarting('A', 'B')
                println("  [STEP 1 - FILTER] Names starting with A or B:")
                filtered.forEach { println("  → ${it.name} (${it.age})") }  // forEach lambda

                // Step 2: extract ages
                val ages = people.extractAges(filtered)
                println("\n  [STEP 2 - MAP] Extracted ages:")
                println("  $ages")

                // Step 3: calculate average (fold inside)
                val average = people.calculateAverage(ages)

                // Step 4: format and print
                println("\n  [STEP 3 & 4 - FOLD & FORMAT] Average age:")
                println("  %.1f".format(average))
            }
            "2" -> {
                val result = people.filterByNameStarting('A', 'B')  // filter lambda
                println("  [FILTER] Names starting with A or B:")
                result.forEach { println("  → ${it.name}, age ${it.age}") }
            }
            "3" -> {
                val filtered = people.filterByNameStarting('A', 'B')
                val ages = people.extractAges(filtered)              // map lambda
                println("  [MAP] Ages of A/B names:")
                println("  $ages")
            }
            "4" -> {
                val total = people.totalAge()                        // fold lambda
                println("  [FOLD] Total age of all people:")
                println("  $total")
            }
            "5" -> {
                val oldest = people.findOldest()                     // fold lambda
                println("  [FOLD] Oldest person:")
                println("  ${oldest?.name}, age ${oldest?.age}")
            }
            "6" -> {
                val result = people.filterByMinAge(25)               // filter lambda
                println("  [FILTER] People aged 25 and above:")
                result.forEach { println("  → ${it.name}, age ${it.age}") }
            }
            "7" -> {
                val names = people.getNames()                        // map lambda
                println("  [MAP] All names:")
                println("  $names")
            }
            "0" -> {
                println("  Goodbye!")
                running = false
            }
            else -> println("  Invalid choice. Please enter a number from 0 to 7.")
        }
    }
}

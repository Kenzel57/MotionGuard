package com.example.gradecalculatorr

data class StudentResult(
    val id: String,
    val name: String,
    val marks: List<Double>,
    val total: Double,
    val average: Double,
    val grade: String
)
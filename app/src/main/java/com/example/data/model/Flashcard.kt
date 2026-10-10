package com.example.data.model

data class Flashcard(
    val id: String,
    val subject: Subject,
    val category: String, // قانون، قاعدة نحوية، مصطلح، تاريخ
    val frontTitle: String,
    val backAnswer: String,
    val jannatAdvice: String
)

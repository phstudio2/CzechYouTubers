package com.phstudio.youtubers.Quiz

data class Question(
    val id: Int,
    val image: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int
)
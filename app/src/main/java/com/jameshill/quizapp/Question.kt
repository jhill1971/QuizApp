package com.jameshill.quizapp

data class Question (
    val id: Int, // question id
    val question: String, // question text
    val image: Int, // question image
    val optionOne: String, //answer option
    val optionTwo: String, //answer option
    val optionThree: String, //answer option
    val optionFour: String, //answer option
    val correctOption: Int) //correct response
package com.ironelder.kotlintodolist.data

data class TodoModel(
    val content: String,
    val createdAt: String,
    val done: Boolean,
    val id: Int,
    val seq: Int,
    val updatedAt: String?
)
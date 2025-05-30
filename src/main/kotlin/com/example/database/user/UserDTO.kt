package com.example.database.user

data class UserDTO(
    val id: String,
    val username: String,
    val password: String,
    val email: String,
    val created_at: Long
)

package com.example.features.user

import com.example.database.user.User
import io.ktor.server.application.*
import java.time.LocalTime

class UserController(val call: ApplicationCall) {

    val bcrypt = at.favre.lib.crypto.bcrypt.BCrypt.withDefaults()

    fun createUser(username: String, email: String, password: String): UserModel {
        val passwordHash = bcrypt.hashToString(12, password.toCharArray())
        val user = UserModel(
            username = username,
            email = email,
            points = 0,
            passwordHash = passwordHash,
            collectedItems = emptyList(),
            requests = emptyList(),
            createdAt = LocalTime.now()
        )
        User.insert(user)
        return user
    }

    fun getUserInfoById(userId: String): UserModel? {
        return User.fetchUserInfoById(userId)
    }

    fun getUserByUsername(username: String): UserModel? {
        return User.fetchUserByUsername(username)
    }

    fun verifyPassword(username: String, password: String): Boolean {
        return User.isPasswordValid(username, password)
    }
}
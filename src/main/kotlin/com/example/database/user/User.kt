package com.example.database.user

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.database.collected_items.CollectedItems
import com.example.database.requests.Requests
import com.example.database.tips.Tips
import com.example.features.user.CollectedItem
import com.example.features.user.RequestModel
import com.example.features.user.UserModel
import io.ktor.http.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.util.UUID

object User: Table("users") {
    private val bcrypt = at.favre.lib.crypto.bcrypt.BCrypt.withDefaults()

    val user_id = User.text("user_id")
    val username = User.text("username").uniqueIndex()
    val email = User.text("email").uniqueIndex()
    val points = User.integer("points")
    val passwordHash = User.text("password_hash")
    val created_at = User.timestamp("created_at")

    fun insert(user: UserModel) {
        transaction {
            User.insert {
                it[user_id] = user.id
                it[username] = user.username
                it[email] = user.email
                it[points] = 0
                it[passwordHash] = user.passwordHash
                it[created_at] = Instant.now()
            }
            CollectedItems.insert {
                it[collected_items_id] = UUID.randomUUID().toString()
                it[user_id] = user.id
                it[batteries_unit] = 0
                it[plastic_unit] = 0
                it[paper_unit] = 0
            }
        }
    }

    fun fetchUserInfoById(userId: String): UserModel? {
        return try {
            transaction {
                val requests: List<RequestModel> = Requests.selectAll()
                    .mapNotNull {
                        if (it[Requests.user_id].toString() == userId) {
                            RequestModel(
                                requestNumber = it[Requests.request_number],
                                title = it[Requests.title],
                                status = it[Requests.status]
                            )
                        } else {
                            null // обязательно указать null для mapNotNull
                        }
                    }

                val tip = Tips.selectAll().map { it[Tips.text] }.toList().random()

                val collectedItems = CollectedItems.select { CollectedItems.user_id eq userId}.map {
                    CollectedItem(
                        batteriesUnit = it[CollectedItems.batteries_unit],
                        paperUnit = it[CollectedItems.paper_unit],
                        plasticUnit = it[CollectedItems.plastic_unit]
                    )
                }.toList()

                print(collectedItems)

                val user = User.select { User.user_id eq userId }.forEach {
                    if(it[User.user_id].toString() == userId) {
                        return@transaction UserModel(
                            id = it[User.user_id],
                            username = it[User.username],
                            email = it[User.email],
                            passwordHash = it[User.passwordHash],
                            points = it[User.points],
                            tip = tip,
                            requests = requests,
                            collectedItems = collectedItems,
                            createdAt = LocalTime.ofInstant(it[User.created_at], ZoneId.systemDefault())
                        )
                    }
                }
                return@transaction null
            }
        } catch(e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun fetchUserByUsername(username: String): UserModel? {
        return try {
            transaction {
                val user = User.select { User.username eq username }.map {
                    UserModel(
                        id = it[User.user_id],
                        username = it[User.username],
                        email = it[User.email],
                        passwordHash = it[User.passwordHash],
                        createdAt = LocalTime.ofInstant(it[User.created_at], ZoneId.systemDefault())
                    )
                }.toList()
                return@transaction user.firstOrNull()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun isPasswordValid(username: String, password: String): Boolean {
        val user = fetchUserByUsername(username) ?: return false
        return BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash).verified
    }
}
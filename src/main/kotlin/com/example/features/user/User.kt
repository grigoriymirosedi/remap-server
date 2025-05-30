package com.example.features.user

import com.example.database.collected_items.CollectedItems
import com.example.database.core.serializers.LocalTimeSerializer
import kotlinx.serialization.Serializable
import java.io.Serial
import java.net.Authenticator.RequestorType
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


@Serializable
data class UserModel(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String,
    val points: Int = 0,
    val passwordHash: String,
    val tip: String = "",
    val requests: List<RequestModel> = emptyList(),
    val collectedItems: List<CollectedItem> = emptyList(),
    @Serializable(with = LocalTimeSerializer::class)
    val createdAt: LocalTime
)

@Serializable
data class UserCreateRequest(
    val username: String,
    val email: String,
    val password: String
)

@Serializable
data class UserLoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class UserInfoResponse(
    val username: String,
    val email: String,
    val points: Int,
    val tip: String,
    val requests: List<RequestModel>,
    val collectedItems: List<CollectedItem>,
)

@Serializable
data class AuthResponse(
    val token: String,
)

@Serializable
data class RequestModel(
    val requestNumber: String,
    val title: String,
    val status: Byte
)

@Serializable
data class CollectedItem(
    val batteriesUnit: Int,
    val plasticUnit: Int,
    val paperUnit: Int,
)
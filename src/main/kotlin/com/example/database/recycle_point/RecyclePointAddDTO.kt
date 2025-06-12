package com.example.database.recycle_point

import kotlinx.serialization.Serializable

@Serializable
data class RecyclePointAddDTO(
    val id: String,
    val name: String,
    val description: String,
    val address: String,
    val locationHint: String? = null,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String? = null,
    val categories: List<String>,
    val working_hours: String,
    val phoneNumber: String ? = null,
    val moderationStatus: Int = 0
)

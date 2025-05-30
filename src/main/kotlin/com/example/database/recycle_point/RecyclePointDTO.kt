package com.example.database.recycle_point

import com.example.features.recycle_point.RecyclePointResponse
import kotlinx.serialization.Serializable

@Serializable
data class RecyclePointDTO(
    val id: String,
    val name: String,
    val description: String,
    val address: String,
    val locationHint: String,
    val latitude: Double,
    val longitude: Double,
    val working_hours: String,
    val phone_number: String,
    val imageUrl: String,
    val moderationStatus: Int
)
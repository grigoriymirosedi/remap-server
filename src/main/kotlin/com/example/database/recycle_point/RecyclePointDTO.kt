package com.example.database.recycle_point

import com.example.features.recycle_point.RecyclePointResponse
import kotlinx.serialization.Serializable

@Serializable
data class RecyclePointDTO(
    val id: String,
    val name: String,
    val image: String?,
    val description: String,
    val contacts: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val working_hours: String,
)
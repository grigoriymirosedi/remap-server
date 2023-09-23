package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class RecyclePointDTO(
    val id: Int,
    val name: String,
    val image: String?,
    val description: String,
    val contacts: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val working_hours: String,
)
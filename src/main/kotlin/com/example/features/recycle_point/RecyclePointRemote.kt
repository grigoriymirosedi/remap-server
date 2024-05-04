package com.example.features.recycle_point

import com.example.database.recycle_point.RecyclePointDTO
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class RecyclePointReceive(
    val name: String,
    val image: String?,
    val description: String,
    val contacts: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val working_hours: String,
)

fun RecyclePointReceive.toRecyclePointDTO(): RecyclePointDTO =
    RecyclePointDTO(
        id = UUID.randomUUID().toString(),
        name = name,
        image = image,
        description = description,
        contacts = contacts,
        latitude = latitude,
        longitude = longitude,
        address = address,
        working_hours = working_hours
    )

@Serializable
data class RecyclePointResponse(
    val id: String,
    val name: String,
    val image: String?,
    val description: String,
    val contacts: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val working_hours: String
)

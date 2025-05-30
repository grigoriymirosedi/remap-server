package com.example.features.recycle_point

import com.example.database.recycle_point.RecyclePointDTO
import com.example.features.event.EventResponse
import com.example.features.user.CollectedItem
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class RecyclePointReceive(
    val name: String,
    val description: String,
    val address: String,
    val locationHint: String,
    val latitude: Double,
    val longitude: Double,
    val working_hours: String,
    val phoneNumber: String,
    val imageUrl: String? = null,
    val moderationStatus: Int
)

fun RecyclePointReceive.toRecyclePointDTO(): RecyclePointDTO =
    RecyclePointDTO(
        id = UUID.randomUUID().toString(),
        name = name,
        description = description,
        address = address,
        locationHint = locationHint,
        latitude = latitude,
        longitude = longitude,
        working_hours = working_hours,
        phone_number = phoneNumber,
        imageUrl = imageUrl ?: "",
        moderationStatus = moderationStatus
    )

@Serializable
data class RecyclePointResponse(
    val id: String,
    val name: String,
    val description: String,
    val address: String,
    val locationHint: String,
    val latitude: Double,
    val longitude: Double,
    val working_hours: String,
    val phoneNumber: String,
    val imageUrl: String,
    val moderationStatus: Int,
    val categories: List<String>
)

@Serializable
data class AllDataResponse(
    val username: String,
    val points: Int,
    val tip: String,
    val collected_units: List<CollectedItem>,
    val recycle_points_nearby: List<RecyclePointResponse>,
    val upcoming_events: List<EventResponse>
)

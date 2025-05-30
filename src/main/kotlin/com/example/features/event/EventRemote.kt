package com.example.features.event

import com.example.database.core.serializers.LocalDateSerializer
import com.example.database.core.serializers.LocalTimeSerializer
import com.example.database.event.Event
import com.example.database.event.EventDTO
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Serializable
data class EventResponse(
    val id: String,
    val title: String,
    val description: String,
    val image_url: String,
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val time: LocalTime,
    val location: String,
)

@Serializable
data class EventReceive(
    val title: String,
    val description: String,
    val photo_url: String,
    @Serializable(with = LocalDateSerializer::class)
    val event_date: LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val event_start_time: LocalTime,
    val event_location: String,
)

fun EventReceive.toEventDTO(): EventDTO = EventDTO(
    id = UUID.randomUUID().toString(),
    title = title,
    description = description,
    photo_url = photo_url,
    event_date = event_date,
    event_start_time = event_start_time,
    event_location = event_location,
)
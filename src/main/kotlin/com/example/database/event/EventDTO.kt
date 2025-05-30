package com.example.database.event

import com.example.database.core.serializers.LocalDateSerializer
import com.example.database.core.serializers.LocalTimeSerializer
import com.example.features.event.EventResponse
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class EventDTO(
    val id: String,
    val title: String,
    val description: String,
    val photo_url: String,
    @Serializable(with = LocalDateSerializer::class)
    val event_date: LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val event_start_time: LocalTime,
    val event_location: String,
)

fun EventDTO.toEventResponse() = EventResponse (
    id = id,
    title = title,
    description = description,
    image_url = photo_url,
    date = event_date,
    time = event_start_time,
    location = event_location,
)


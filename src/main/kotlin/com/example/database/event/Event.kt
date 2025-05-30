package com.example.database.event

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Event : Table("events") {
    val id = Event.text("event_id")
    val title = Event.text("title")
    val description = Event.text("description")
    val image_url = Event.text("image_url")
    val date = Event.date("date")
    val time = Event.time("time")
    val location = Event.text("location")

    fun insert(eventDTO: EventDTO) {
        transaction {
            Event.insert {
                it[id] = eventDTO.id
                it[title] = eventDTO.title
                it[description] = eventDTO.description
                it[image_url] = eventDTO.photo_url
                it[date] = eventDTO.event_date
                it[time] = eventDTO.event_start_time
                it[location] = eventDTO.event_location
            }
        }
    }

    fun fetchAllEvents(): List<EventDTO> {
        return try {
            transaction {
                Event.selectAll().toList()
                    .map {
                        EventDTO(
                            id = it[Event.id],
                            title = it[Event.title],
                            description = it[Event.description],
                            photo_url = it[Event.image_url],
                            event_date = it[Event.date],
                            event_start_time = it[Event.time],
                            event_location = it[Event.location],
                        )
                    }
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            emptyList()
        }
    }
}
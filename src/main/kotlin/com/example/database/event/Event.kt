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
    val photo_url = Event.text("photo_url")
    val event_date = Event.date("event_date")
    val event_start_time = Event.time("event_start_time")
    val event_location = Event.text("event_location")
    val event_color = Event.varchar("event_color", 7)

    fun insert(eventDTO: EventDTO) {
        transaction {
            Event.insert {
                it[id] = eventDTO.id
                it[title] = eventDTO.title
                it[description] = eventDTO.description
                it[photo_url] = eventDTO.photo_url
                it[event_date] = eventDTO.event_date
                it[event_start_time] = eventDTO.event_start_time
                it[event_location] = eventDTO.event_location
                it[event_color] = eventDTO.event_color
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
                            photo_url = it[Event.photo_url],
                            event_date = it[Event.event_date],
                            event_start_time = it[Event.event_start_time],
                            event_location = it[Event.event_location],
                            event_color = it[Event.event_color]
                        )
                    }
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            emptyList()
        }
    }
}
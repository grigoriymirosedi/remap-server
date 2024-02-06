package com.example.features.event

import com.example.database.event.Event
import com.example.database.event.toEventResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class EventController(private val call: ApplicationCall) {

    suspend fun fetchAllEvents() {
        val events = Event.fetchAllEvents().map { it.toEventResponse() }
        call.respond(events)
    }

    suspend fun createEvent() {
        val eventReceive = call.receive<EventReceive>()
        val eventDTO = eventReceive.toEventDTO()
        Event.insert(eventDTO = eventDTO)
        call.respond(eventDTO)
    }

}
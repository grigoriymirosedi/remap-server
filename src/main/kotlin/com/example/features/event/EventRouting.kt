package com.example.features.event
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureEventRouting() {
    routing {
        get("/v1/events") {
            EventController(call).fetchAllEvents()
        }

        post("/v1/event") {
            EventController(call).createEvent()
        }
    }
}
package com.example.routes

import com.example.database.dao.dao
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRecyclePointRouting() {
    routing {
        get("/v1/recycle-points") {
            call.respond(dao.allRecyclePoints())
        }
    }
}
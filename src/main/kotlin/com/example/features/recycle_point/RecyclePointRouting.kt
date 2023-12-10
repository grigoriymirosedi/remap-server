package com.example.features.recycle_point

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRecyclePointRouting() {
    routing {
        get("/v1/recycle-points") {
            RecyclePointController(call).fetchAllRecyclePoint()
        }
        post("/v1/recycle-point") {
            RecyclePointController(call).createRecyclePoint()
        }
    }
}
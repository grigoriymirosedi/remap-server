package com.example.features.recycle_point

import com.example.utils.convertToCategoryId
import com.example.utils.toCategoryId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRecyclePointRouting() {
    routing {
        get("/v1/recycle-points") {
            RecyclePointController(call).fetchAllRecyclePoint()
        }
        post("/v1/recycle-points") {
            val categoryType: List<String> = call.request.queryParameters.getAll("categoryType") ?: emptyList()
            if(!categoryType.isEmpty()) {
                val categoryId = categoryType.map{ it.toCategoryId() }
                RecyclePointController(call).createRecyclePoint(categoryId = categoryId)
            } else {
                call.respondText("Missing categoryType value!", status = HttpStatusCode.NoContent)
            }
        }
    }
}
package com.example.features.recycle_point

import com.example.database.recycle_point.RecyclePoint
import com.example.database.recycle_point.toRecyclePointResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class RecyclePointController(private val call: ApplicationCall) {

    suspend fun fetchAllRecyclePoint() {
        val recyclePoints = RecyclePoint.fetchAll().map { it.toRecyclePointResponse() }
        call.respond(recyclePoints)
    }

    suspend fun createRecyclePoint(categoryId: List<String>) {
        val recyclePointReceive = call.receive<RecyclePointReceive>()
        val recyclePoint = recyclePointReceive.toRecyclePointDTO()
        RecyclePoint.insert(recyclePointDTO = recyclePoint, categoryId = categoryId)
        call.respond(recyclePoint)
    }
}
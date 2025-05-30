package com.example.features.recycle_point

import com.example.database.recycle_point.RecyclePoint
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class RecyclePointController(private val call: ApplicationCall) {

    suspend fun fetchAllRecyclePoint(categoryId: List<String>?) {
        val recyclePoints = RecyclePoint.fetchAllByCategories(categoryId = categoryId)
        call.respond(recyclePoints)
    }

    suspend fun createRecyclePoint(categoryId: List<String>) {
        val recyclePointReceive = call.receive<RecyclePointReceive>()
        val recyclePoint = recyclePointReceive.toRecyclePointDTO()
        RecyclePoint.insert(recyclePointDTO = recyclePoint, categoryId = categoryId)
        call.respond(recyclePoint)
    }
}
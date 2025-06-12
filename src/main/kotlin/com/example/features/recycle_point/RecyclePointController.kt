package com.example.features.recycle_point

import com.example.database.recycle_point.RecyclePoint
import com.example.database.recycle_point.RecyclePointAddDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.update

class RecyclePointController(private val call: ApplicationCall) {

    suspend fun fetchAllRecyclePoint(categoryId: List<String>?) {
        val recyclePoints = RecyclePoint.fetchAllByCategories()
        call.respond(recyclePoints)
    }

    suspend fun createRecyclePoint(userId: String) {
        val recyclePointReceive = call.receive<RecyclePointAddDTO>()
        val recyclePoint = recyclePointReceive
        RecyclePoint.insert(recyclePointDTO = recyclePoint, userId = userId)
        call.respond(recyclePoint)
    }

    suspend fun getAllModerationRecyclePoints() {
        val result = RecyclePoint.fetchAllModerationRecyclePoints()
        call.respond(result)
    }

    suspend fun updateRecyclePointStatus() {
        var updatedResult = call.receive<RecyclePointResponse>()
        RecyclePoint.updateRecyclePointStatus(recyclePoint = updatedResult)
        call.respond(HttpStatusCode.OK, "Status updated successfully")
    }

    suspend fun getRecyclePointById() {
        val recyclePointId = call.receive<RecyclePointIdResponse>()
        val result = RecyclePoint.fetchRecyclePointById(recyclePointId = recyclePointId.recyclePointId)
        call.respond(HttpStatusCode.OK, result)
    }
}
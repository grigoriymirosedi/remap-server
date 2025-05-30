package com.example.features.recycle_point

import com.example.features.user.UserController
import com.example.features.user.UserInfoResponse
import com.example.utils.convertToCategoryId
import com.example.utils.toCategoryId
import com.example.utils.toCategoryType
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRecyclePointRouting() {
    routing {

        get("/v1/recycle-points") {
            val categoryType: List<String>? = call.request.queryParameters.getAll("categoryType")
            val categoryId = categoryType?.map { it.toCategoryId() }
            RecyclePointController(call).fetchAllRecyclePoint(categoryId = categoryId)
        }
        post("/v1/recycle-points") {
            val categoryType: List<String>? = call.request.queryParameters.getAll("categoryType")
            if(!categoryType.isNullOrEmpty()) {
                val categoryId = categoryType.map{ it.toCategoryId() }
                RecyclePointController(call).createRecyclePoint(categoryId = categoryId)
            } else {
                call.respondText("Missing categoryType value!", status = HttpStatusCode.NoContent)
            }
        }
    }
}
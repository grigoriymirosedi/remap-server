package com.example.features.recycle_point

import com.example.database.recycle_point.RecyclePoint
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

        authenticate("auth-jwt") {
            post("/v1/recycle-point") {
                val principal = call.principal<JWTPrincipal>()
                val userId = UUID.fromString(principal?.payload?.getClaim("userId")?.asString()).toString()
                RecyclePointController(call).createRecyclePoint(userId = userId)
            }
        }

        get("/v1/admin/recycle-points") {
            RecyclePointController(call).getAllModerationRecyclePoints()
        }

        patch("/v1/admin/recycle-point") {
            RecyclePointController(call).updateRecyclePointStatus()
        }

        patch("v1/recycle-point") {
            RecyclePointController(call).getRecyclePointById()
        }
    }
}
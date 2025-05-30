package com.example.database.security

import com.example.features.user.*
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureSecurityRouting() {
    val dotenv = dotenv()
    val jwtConfig = JwtConfig(
        secret = dotenv["secret"],
        issuer = dotenv["issuer"],
        audience = dotenv["audience"],
        realm = dotenv["realm"],
        accessTokenExpiry = dotenv["accessTokenExpiry"].toLong()
    )

    routing {
        post("/v1/register") {
            val request = call.receive<UserCreateRequest>()
            val userController = UserController(call)
            if (userController.getUserByUsername(request.username) != null) {
                call.respond(HttpStatusCode.Conflict, "Username already exists")
                return@post
            }

            val user = userController.createUser(
                username = request.username,
                email = request.email,
                password = request.password
            )

            val token = generateToken(user, jwtConfig)
            call.respond(
                AuthResponse(
                    token = token,
                )
            )
        }
        post("/v1/login") {
            val request = call.receive<UserLoginRequest>()
            val userController = UserController(call)

            val user = userController.getUserByUsername(request.username)
                ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "User does not exist")
                    return@post
                }

            if (!userController.verifyPassword(request.username, request.password)) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid password")
                return@post
            }

            val token = generateToken(user, jwtConfig)
            call.respond(
                AuthResponse(
                    token = token,
                )
            )
        }
        authenticate("auth-jwt") {
            get("/v1/fetchUserInfo") {
                val principal = call.principal<JWTPrincipal>()
                val userId = UUID.fromString(principal?.payload?.getClaim("userId")?.asString()).toString()
                val userController = UserController(call)

                val user = userController.getUserInfoById(userId)
                    ?: throw IllegalArgumentException("User not found")

                call.respond(
                   UserInfoResponse(
                       username = user.username,
                       email = user.email,
                       points = user.points,
                       tip = user.tip,
                       requests = user.requests,
                       collectedItems = user.collectedItems
                   )
                )
            }
        }
    }
}
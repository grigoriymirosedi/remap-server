package com.example.database.security

import io.ktor.server.application.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.features.user.UserModel
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import java.util.*

fun Application.configureSecurity() {
    val dotenv = dotenv()
    val jwtConfig = JwtConfig(
        secret = dotenv["secret"],
        issuer = dotenv["issuer"],
        audience = dotenv["audience"],
        realm = dotenv["realm"],
        accessTokenExpiry = dotenv["accessTokenExpiry"].toLong()
    )

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtConfig.secret))
                    .withAudience(jwtConfig.audience)
                    .withIssuer(jwtConfig.issuer)
                    .build()
            )
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()
                if (userId.isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

}

data class JwtConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val accessTokenExpiry: Long
)

internal fun generateToken(user: UserModel, jwtConfig: JwtConfig): String {
    return JWT.create()
        .withAudience(jwtConfig.audience)
        .withIssuer(jwtConfig.issuer)
        .withClaim("userId", user.id)
        .withExpiresAt(Date(System.currentTimeMillis() + jwtConfig.accessTokenExpiry))
        .sign(Algorithm.HMAC256(jwtConfig.secret))
}
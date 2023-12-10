package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.features.recycle_point.configureRecyclePointRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = System.getenv("DATABASE_CONNECTION_STRING"),
        driver = System.getenv("JDBC_DRIVER"),
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD"),
    )

    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRecyclePointRouting()
    configureRouting()
}

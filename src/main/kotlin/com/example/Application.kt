package com.example

import com.example.database.security.configureSecurity
import com.example.database.security.configureSecurityRouting
import com.example.features.event.configureEventRouting
import com.example.plugins.configureSerialization
import com.example.features.recycle_point.configureRecyclePointRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    Database.connect(
        url = System.getenv("DATABASE_CONNECTION_STRING"),
        driver = System.getenv("JDBC_DRIVER"),
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD"),
    )

    embeddedServer(Netty,
        host = "192.168.0.108", port = System.getenv("PORT").toInt(),module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureRecyclePointRouting()
    configureEventRouting()
    configureSecurityRouting()
}

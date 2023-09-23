package com.example.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

// TODO: Delete it later?
fun Application.configureDatabases() {
    Database.connect(
        url = System.getenv("DATABASE_CONNECTION_STRING"),
        driver = System.getenv("JDBC_DRIVER"),
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD"),
    )
}
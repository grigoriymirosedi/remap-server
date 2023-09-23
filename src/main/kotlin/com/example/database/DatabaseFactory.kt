package com.example.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


object DatabaseFactory {
    fun init() {
        Database.connect(
            url = System.getenv("DATABASE_CONNECTION_STRING"),
            driver = System.getenv("JDBC_DRIVER"),
            user = System.getenv("POSTGRES_USER"),
            password = System.getenv("POSTGRES_PASSWORD"),
        )

    }

    // TODO: Decide whether to use dbQuery or not!
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
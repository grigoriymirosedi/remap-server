package com.example.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object RecyclePoint: Table("recycle_point") {
    val id = RecyclePoint.integer("id").autoIncrement()
    val name = RecyclePoint.text("name")
    val image = RecyclePoint.text("image")
    val description = RecyclePoint.text("description")
    val contacts = RecyclePoint.text("contacts")
    val latitude = RecyclePoint.double("latitude")
    val longitude = RecyclePoint.double("longitude")
    val address = RecyclePoint.text("address")
    val working_hours = RecyclePoint.text("working_hours")


    // TODO() Maybe delete it later?
    fun fetchAll(): List<RecyclePointDTO> {
        return try {
            transaction {
                RecyclePoint.selectAll().toList()
                    .map {
                        RecyclePointDTO(
                            id = it[RecyclePoint.id],
                            name = it[name],
                            image = it[image],
                            description = it[description],
                            contacts = it[contacts],
                            latitude = it[latitude],
                            longitude = it[longitude],
                            address = it[address],
                            working_hours = it[working_hours]
                        )
                    }
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            emptyList()
        }
    }
}
package com.example.database.recycle_point

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object RecyclePoint: Table("recycle_points") {
    val id = RecyclePoint.text("recycle_point_id")
    val name = RecyclePoint.text("name")
    val image = RecyclePoint.text("image").nullable()
    val description = RecyclePoint.text("description")
    val contacts = RecyclePoint.text("contacts")
    val latitude = RecyclePoint.double("latitude")
    val longitude = RecyclePoint.double("longitude")
    val address = RecyclePoint.text("address")
    val working_hours = RecyclePoint.text("working_hours")


    fun insert(recyclePointDTO: RecyclePointDTO) {
        transaction {
            RecyclePoint.insert {
                it[id] = recyclePointDTO.id
                it[name] = recyclePointDTO.name
                it[image] = recyclePointDTO.image
                it[description] = recyclePointDTO.description
                it[contacts] = recyclePointDTO.contacts
                it[latitude] = recyclePointDTO.latitude
                it[longitude] = recyclePointDTO.longitude
                it[address] = recyclePointDTO.address
                it[working_hours] = recyclePointDTO.working_hours
            }
        }
    }

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
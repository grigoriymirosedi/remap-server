package com.example.database.recycle_point

import com.example.database.recycle_point_category.RecyclePointCategory
import com.example.features.recycle_point.RecyclePointController
import com.example.features.recycle_point.RecyclePointReceive
import com.example.features.recycle_point.RecyclePointResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.transaction

object RecyclePoint : Table("recycle_points") {
    val id = RecyclePoint.text("recycle_point_id")
    val name = RecyclePoint.text("name")
    val image = RecyclePoint.text("image").nullable()
    val description = RecyclePoint.text("description")
    val contacts = RecyclePoint.text("contacts")
    val latitude = RecyclePoint.double("latitude")
    val longitude = RecyclePoint.double("longitude")
    val address = RecyclePoint.text("address")
    val working_hours = RecyclePoint.text("working_hours")


    fun insert(recyclePointDTO: RecyclePointDTO, categoryId: List<String>) {
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

            categoryId.forEach { categoryId ->
                RecyclePointCategory.insert {
                    it[this.recyclePointId] = recyclePointDTO.id
                    it[this.categoryId] = categoryId
                }
            }

//            RecyclePointCategory.insert {
//                it[this.recyclePointId] = recyclePointDTO.id
//                it[this.categoryId] = categoryId
//            }

        }
    }

    fun fetchAll(categoryId: List<String>): List<RecyclePointResponse> {
        return try {
            transaction {
                RecyclePoint.join(RecyclePointCategory, JoinType.INNER, RecyclePoint.id, RecyclePointCategory.recyclePointId)
                    .select { RecyclePointCategory.categoryId inList categoryId }
                    .groupBy(RecyclePoint.id)
                    .mapNotNull { row ->
                        val categories = row[RecyclePointCategory.categoryId].map { it.toString() }.toList()
                        val response = RecyclePointResponse(
                            id = row[RecyclePoint.id].toString(),
                            name = row[RecyclePoint.name],
                            image = row[RecyclePoint.image],
                            description = row[RecyclePoint.description],
                            contacts = row[RecyclePoint.contacts],
                            latitude = row[RecyclePoint.latitude],
                            longitude = row[RecyclePoint.longitude],
                            address = row[RecyclePoint.address],
                            working_hours = row[RecyclePoint.working_hours],
                            categories = categories
                        )
                        response
                    }
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            emptyList()
        }
    }

//    fun fetchRecycePointsByCategory(category: String) {
//        return try {
//            transaction {
//                RecyclePoint
//            }
//        }
//    }
}
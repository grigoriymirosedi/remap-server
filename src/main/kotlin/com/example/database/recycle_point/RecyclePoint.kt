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

    fun fetchAllByCategories(categoryId: List<String>?): List<RecyclePointResponse> {
        return try {
            transaction {
                val query = (RecyclePoint innerJoin RecyclePointCategory)
                    .slice(
                        RecyclePoint.id,
                        RecyclePoint.name,
                        RecyclePoint.image,
                        RecyclePoint.description,
                        RecyclePoint.contacts,
                        RecyclePoint.latitude,
                        RecyclePoint.longitude,
                        RecyclePoint.address,
                        RecyclePoint.working_hours
                    )

                val filteredQuery = if (categoryId != null) {
                    query.select { RecyclePointCategory.categoryId inList categoryId }
                } else {
                    query.selectAll()
                }

                filteredQuery.groupBy(RecyclePoint.id)
                    .map {
                        val id = it[RecyclePoint.id]
                        val categories = RecyclePointCategory
                            .select { RecyclePointCategory.recyclePointId eq id }
                            .map { it[RecyclePointCategory.categoryId] }
                            .toList()

                        RecyclePointResponse(
                            id = it[RecyclePoint.id],
                            name = it[RecyclePoint.name],
                            image = it[RecyclePoint.image],
                            description = it[RecyclePoint.description],
                            contacts = it[RecyclePoint.contacts],
                            latitude = it[RecyclePoint.latitude],
                            longitude = it[RecyclePoint.longitude],
                            address = it[RecyclePoint.address],
                            working_hours = it[RecyclePoint.working_hours],
                            categories = categories
                        )
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
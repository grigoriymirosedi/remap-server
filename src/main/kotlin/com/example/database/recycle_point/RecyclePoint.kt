package com.example.database.recycle_point

import com.example.database.collected_items.CollectedItems
import com.example.database.event.Event
import com.example.database.recycle_point_category.RecyclePointCategory
import com.example.database.tips.Tips
import com.example.database.user.User
import com.example.features.event.EventResponse
import com.example.features.recycle_point.AllDataResponse
import com.example.features.recycle_point.RecyclePointResponse
import com.example.features.user.CollectedItem
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object RecyclePoint : Table("recycle_points") {
    val recycle_point_id = RecyclePoint.text("recycle_point_id")
    val name = RecyclePoint.text("name")
    val description = RecyclePoint.text("description")
    val address = RecyclePoint.text("address")
    val location_hint = RecyclePoint.text("location_hint")
    val latitude = RecyclePoint.double("latitude")
    val longitude = RecyclePoint.double("longitude")
    val working_hours = RecyclePoint.text("working_hours")
    val phone_number = RecyclePoint.text("phone_number")
    val imageUrl = RecyclePoint.text("image_url").nullable()
    val moderation_status = RecyclePoint.byte("moderation_status")


    fun insert(recyclePointDTO: RecyclePointDTO, categoryId: List<String>) {
        transaction {
            RecyclePoint.insert {
                it[recycle_point_id] = recyclePointDTO.id
                it[name] = recyclePointDTO.name
                it[description] = recyclePointDTO.description
                it[address] = recyclePointDTO.address
                it[location_hint] = recyclePointDTO.locationHint
                it[latitude] = recyclePointDTO.latitude
                it[longitude] = recyclePointDTO.longitude
                it[working_hours] = recyclePointDTO.working_hours
                it[phone_number] = recyclePointDTO.phone_number
                it[imageUrl] = recyclePointDTO.imageUrl
                it[moderation_status] = recyclePointDTO.moderationStatus.toByte()
            }

            categoryId.forEach { categoryId ->
                RecyclePointCategory.insert {
                    it[this.recyclePointId] = recyclePointDTO.id
                    it[this.categoryId] = categoryId
                }
            }
        }
    }

    fun fetchAllByCategories(categoryId: List<String>?): List<RecyclePointResponse> {
        return try {
            transaction {
                val query = (RecyclePoint innerJoin RecyclePointCategory)
                    .slice(
                        RecyclePoint.recycle_point_id,
                        RecyclePoint.name,
                        RecyclePoint.description,
                        RecyclePoint.address,
                        RecyclePoint.location_hint,
                        RecyclePoint.latitude,
                        RecyclePoint.longitude,
                        RecyclePoint.working_hours,
                        RecyclePoint.phone_number,
                        RecyclePoint.imageUrl,
                        RecyclePoint.moderation_status
                    )

                val filteredQuery = if (categoryId != null) {
                    query.select { RecyclePointCategory.categoryId inList categoryId }
                } else {
                    query.selectAll()
                }

                filteredQuery.groupBy(RecyclePoint.recycle_point_id)
                    .map {
                        val id = it[RecyclePoint.recycle_point_id]
                        val categories = RecyclePointCategory
                            .select { RecyclePointCategory.recyclePointId eq id }
                            .map { it[RecyclePointCategory.categoryId] }
                            .toList()

                        RecyclePointResponse(
                            id = it[RecyclePoint.recycle_point_id],
                            name = it[RecyclePoint.name],
                            description = it[RecyclePoint.description],
                            address = it[RecyclePoint.address],
                            locationHint = it[RecyclePoint.location_hint],
                            latitude = it[RecyclePoint.latitude],
                            longitude = it[RecyclePoint.longitude],
                            working_hours = it[RecyclePoint.working_hours],
                            phoneNumber = it[RecyclePoint.phone_number],
                            imageUrl = it[RecyclePoint.imageUrl] ?: "",
                            categories = categories,
                            moderationStatus = it[RecyclePoint.moderation_status].toInt()
                        )
                    }
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            emptyList()
        }
    }
}

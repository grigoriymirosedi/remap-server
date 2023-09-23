package com.example.database.dao

import com.example.models.RecyclePointDTO

interface DAOFacade {
    suspend fun allRecyclePoints(): List<RecyclePointDTO>
    suspend fun recyclePoint(id: Int): RecyclePointDTO?
    suspend fun addNewRecyclePoint(
        id: Int,
        name: String,
        image: String?,
        description: String,
        contacts: String,
        latitude: Double,
        longitude: Double,
        address: String,
        working_hours: String
    ): RecyclePointDTO?
}

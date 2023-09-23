package com.example.database.dao

import com.example.database.DatabaseFactory.dbQuery
import com.example.models.RecyclePoint
import com.example.models.RecyclePointDTO
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOFacadeImpl: DAOFacade {

    private fun resultRowToRecyclePoint(row: ResultRow) = RecyclePointDTO(
        id = row[RecyclePoint.id],
        name = row[RecyclePoint.name],
        image = row[RecyclePoint.image],
        description = row[RecyclePoint.description],
        contacts = row[RecyclePoint.contacts],
        latitude = row[RecyclePoint.latitude],
        longitude = row[RecyclePoint.longitude],
        address = row[RecyclePoint.address],
        working_hours = row[RecyclePoint.working_hours]
    )

    override suspend fun allRecyclePoints(): List<RecyclePointDTO> = dbQuery {
        RecyclePoint.selectAll().map(::resultRowToRecyclePoint)
    }

    override suspend fun recyclePoint(id: Int): RecyclePointDTO? = dbQuery {
        RecyclePoint.select { RecyclePoint.id eq id }.map(::resultRowToRecyclePoint).singleOrNull()
    }

    override suspend fun addNewRecyclePoint(
        id: Int,
        name: String,
        image: String?,
        description: String,
        contacts: String,
        latitude: Double,
        longitude: Double,
        address: String,
        working_hours: String
    ): RecyclePointDTO? = dbQuery {
        val insertValue = RecyclePoint.insert {
            it[RecyclePoint.id] = id
            it[RecyclePoint.name] = name
            it[RecyclePoint.image] = image ?: ""
            it[RecyclePoint.description] = description
            it[RecyclePoint.contacts] = contacts
            it[RecyclePoint.address] = address
            it[RecyclePoint.working_hours] = working_hours
        }
        insertValue.resultedValues?.singleOrNull()?.let(::resultRowToRecyclePoint)
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(allRecyclePoints().isEmpty()) {
            addNewRecyclePoint(
                id = hashCode(),
                name = "Test name",
                image = "test image url",
                description = "test description",
                contacts = "+7 928 113 9514",
                latitude = 47.2271,
                longitude = 39.5936,
                address = "Еляна 52",
                working_hours = "Круглосуточно"
            )
        }
    }
}
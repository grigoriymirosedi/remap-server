package com.example.database.collected_items

import com.example.database.user.User
import org.jetbrains.exposed.sql.Table

object CollectedItems: Table("collected_items") {
    val collected_items_id = CollectedItems.text("collected_items_id")
    val user_id = CollectedItems.text("user_id") references User.user_id
    val batteries_unit = CollectedItems.integer("batteries_unit")
    val plastic_unit = CollectedItems.integer("plastic_unit")
    val paper_unit = CollectedItems.integer("paper_unit")
}
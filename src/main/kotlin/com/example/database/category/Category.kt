package com.example.database.category

import org.jetbrains.exposed.sql.Table

object Category: Table("categories") {
    val categoryId = Category.text("category_id")
    val categoryName = Category.text("category_name")
}
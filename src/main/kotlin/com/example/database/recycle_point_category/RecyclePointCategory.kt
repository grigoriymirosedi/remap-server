package com.example.database.recycle_point_category

import com.example.database.category.Category
import com.example.database.recycle_point.RecyclePoint
import org.jetbrains.exposed.sql.Table

object RecyclePointCategory: Table("recycle_point_categories") {
    val recyclePointId = RecyclePointCategory.text("recycle_point_id") references RecyclePoint.recycle_point_id
    val categoryId = RecyclePointCategory.text("category_id") references Category.categoryId
}
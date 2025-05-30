package com.example.database.tips

import org.jetbrains.exposed.sql.Table

object Tips: Table("tips") {
    val tip_id = Tips.text("tip_id")
    val text = Tips.text("text")
}
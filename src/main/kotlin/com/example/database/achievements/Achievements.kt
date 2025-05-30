package com.example.database.achievements

import org.jetbrains.exposed.sql.Table

object Achievements: Table("achievements") {
    val achievement_id = Achievements.text("achievement_id")
    val status = Achievements.byte("status")
}
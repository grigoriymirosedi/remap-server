package com.example.database.user_achievements

import com.example.database.achievements.Achievements
import com.example.database.user.User
import org.jetbrains.exposed.sql.Table

object UserAchievements: Table("user_achievements") {
    val user_id = UserAchievements.text("user_id") references User.user_id
    val achievements_id = UserAchievements.text("achievement_id") references Achievements.achievement_id
}
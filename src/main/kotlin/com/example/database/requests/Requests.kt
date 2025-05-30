package com.example.database.requests

import com.example.database.user.User
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.time

object Requests: Table("requests") {
    val request_id = Requests.text("request_id")
    val user_id = Requests.text("user_id") references User.user_id
    val request_number = Requests.text("request_number")
    val title = Requests.text("title")
    val status = Requests.byte("status")
    val created_at = Requests.time("created_at")


}
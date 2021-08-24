package com.roshd.socketexample.data.models

import com.beust.klaxon.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(@Json(name = "message") val text: String)

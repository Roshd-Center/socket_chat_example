package com.roshd.socketexample.data.models

import com.beust.klaxon.Json

data class Message(@Json(name = "message") val text: String)

package com.roshd.socketexample.data.models

import com.beust.klaxon.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class Message(val message: String)

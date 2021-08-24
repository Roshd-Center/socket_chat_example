package com.roshd.socketexample.data

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import okhttp3.*
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import java.util.concurrent.TimeUnit


const val BASE_URL = "ws://127.0.0.1:8000/ws/chat/akbar/"

val okHttpClient = OkHttpClient.Builder()
    .writeTimeout(500, TimeUnit.MILLISECONDS)
    .readTimeout(500, TimeUnit.MILLISECONDS)
    .build()

val scarlet = Scarlet.Builder()
    .webSocketFactory(okHttpClient.newWebSocketFactory(BASE_URL))
    .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
    .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
    .build()
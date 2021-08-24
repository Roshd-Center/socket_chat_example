package com.roshd.socketexample.data

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import okhttp3.*
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import java.util.concurrent.TimeUnit

fun createAppForegroundLifecycle(application: Application): Lifecycle {
    return AndroidLifecycle.ofApplicationForeground(application)
}

fun getScarlet(application:Application) : Scarlet {
    val URL = "ws://192.168.100.14:8000/ws/chat/akbar/"

    // Building OKHttp client.
    val okHttpClient = OkHttpClient.Builder()
        .writeTimeout(500, TimeUnit.MILLISECONDS)
        .readTimeout(500, TimeUnit.MILLISECONDS)
        .build()

    // Building Moshi message adapter instance.
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Building Scarlet instance.
    return Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(URL))
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
        .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
        .lifecycle(createAppForegroundLifecycle(application))
        .build()
}
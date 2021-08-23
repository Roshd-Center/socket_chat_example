package com.roshd.socketexample

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test_ktor_ws() {
        val client = HttpClient {
            install(WebSockets)
        }

        runBlocking {
            client.webSocket(
                method = HttpMethod.Get,
                host = "127.0.0.1",
                port = 8000, path = "/ws/chat/akbar/"
            ) {

            }
        }
    }
}
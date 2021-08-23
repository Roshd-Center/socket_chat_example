package com.roshd.socketexample

import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import com.roshd.socketexample.data.models.Message
//import com.tinder.scarlet.WebSocket
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import okhttp3.internal.wait

//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.io.PrintWriter
//import java.net.Socket

class KtorWSActivity : AppCompatActivity() {
    var button: Button? = null
    var editText: EditText? = null
    var textView: TextView? = null

    private val TAG = "KtorWSActivity TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val policy =
//            StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)

        button = findViewById<View>(R.id.sendBtn) as Button
        textView = findViewById<View>(R.id.receivedTextView) as TextView
        editText = findViewById<View>(R.id.sendEditText) as EditText

        val client = HttpClient {
            install(WebSockets)
        }


        runBlocking {

//            textView!!.text = "akbar"
            client.webSocket(
                method = HttpMethod.Get,
                host = "192.168.1.6",
                port = 8000, path = "/ws/chat/akbar/"
            ) { // this: DefaultClientWebSocketSession

                //   Send text frame.
//                send("Hello, Text frame")

                // Receive frame.
//                val frame = incoming.receive()
//                when (frame) {
//                    is Frame.Text -> println(frame.readText())
//                    is Frame.Binary -> println(frame.readBytes())
//                }
            }
        }
            button!!.setOnClickListener {
                Log.i(TAG, "Button clicked")

            }

    }

} 
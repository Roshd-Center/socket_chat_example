package com.roshd.socketexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.roshd.socketexample.data.models.Message
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*

class KtorWSActivity : AppCompatActivity() {

    companion object {
        const val HOST = "192.168.1.6"  // Note: "localhost" or "128.0.0.1" is invalid according to Android OS policies
        const val PORT = 8000
        const val PATH = "/ws/chat/a_chat_room/"  // Chat room: a_chat_room
    }

    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var textView: TextView

    private val TAG = "KtorWSActivity TAG"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding UI views
        button = findViewById<View>(R.id.sendBtn) as Button
        textView = findViewById<View>(R.id.receivedTextView) as TextView
        editText = findViewById<View>(R.id.sendEditText) as EditText

        // Instancing Ktor Http client
        val client = HttpClient {
            // Installing WebSockets feature into the http client
            install(WebSockets)
        }

        // Start a Coroutine to Receiving websocket messages:
        CoroutineScope(Dispatchers.IO).launch {
            // Start a websocket session wrapped by the coroutine
            client.webSocket(
                method = HttpMethod.Get,
                host = HOST,
                port = PORT, path = PATH
            ) {
                while (true) {
                    val frame = incoming.receive()  // Waiting for incoming messages
                    if (frame is Frame.Text) {
                        // Parse the received json message to a Message instance.
                        val message: Message? = Klaxon().parse<Message>(frame.readText())

                        // Post the UI view, to update textview with the received message.
                        textView.post {
                            textView.text = textView.text.toString() + "\n" + message!!.text
                        }
                    }
                }

            }

        }

        // Setting up send button listener:
        button.setOnClickListener {
            runBlocking {
                // Start a websocket session again! TODO: requires revision
                client.webSocket(
                    method = HttpMethod.Get,
                    host = HOST,
                    port = PORT, path = PATH
                ) {
                    // Convert editText message into a Message Instance.
                    val msg_to_send = Klaxon().toJsonString(Message(editText.text.toString()))

                    // Send the message toward the server
                    send(msg_to_send)
                }
            }
        }

    }

} 
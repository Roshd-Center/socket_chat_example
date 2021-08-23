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
    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var textView: TextView

    private val TAG = "KtorWSActivity TAG"

    @SuppressLint("SetTextI18n")
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


//        button.setOnClickListener {
        CoroutineScope(Dispatchers.IO).launch {
            client.webSocket(
                method = HttpMethod.Get,
                host = "192.168.1.6",
                port = 8000, path = "/ws/chat/akbar/"
            ) {
                while (true) {
                    val frame = incoming.receive()
                    if (frame is Frame.Text) {
                        val message: Message? = Klaxon().parse<Message>(frame.readText())
                        textView.post {
                            textView.text = textView.text.toString() + "\n" + message!!.text
                        }
                    }
                }

            }

        }

        button.setOnClickListener {
            runBlocking {
                client.webSocket(
                    method = HttpMethod.Get,
                    host = "192.168.1.6",
                    port = 8000, path = "/ws/chat/akbar/"
                ) {
                    send(Klaxon().toJsonString(Message(editText.text.toString())))
                }
            }
        }

    }

} 
package com.roshd.socketexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.roshd.socketexample.data.ChatService
import com.roshd.socketexample.data.chatService
import com.roshd.socketexample.data.models.Message
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Stream.Observer
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.lifecycle.LifecycleRegistry
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.awaitFirst
import org.reactivestreams.Subscription

class ScarletWSActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var textView: TextView

    private val TAG = "ScarletWSActivity TAG"

    private val lifecycleRegistry = LifecycleRegistry()
    lateinit var chatService: ChatService

    private val clientLifecycleRegistry = LifecycleRegistry()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding UI views
        button = findViewById<View>(R.id.sendBtn) as Button
        textView = findViewById<View>(R.id.receivedTextView) as TextView
        editText = findViewById<View>(R.id.sendEditText) as EditText

        chatService = chatService(application)

        button.setOnClickListener {
            chatService.sendMessage(Message(editText.text.toString()))
            editText.text.clear()
        }

        chatService.observeEvents().start(MyObserver())

    }

    class MyObserver(): Observer<WebSocket.Event>{
        val TAG = "ScarletWSActivity Obs"
        override fun onComplete() {
            Log.d(TAG, "onComplete")
        }

        override fun onError(throwable: Throwable) {
            Log.d(TAG, "onError: "+throwable.toString())
        }

        override fun onNext(data: WebSocket.Event) {
            Log.d(TAG, "onNext: "+data.toString())
        }


    }

    class MySubscriber(): org.reactivestreams.Subscriber<WebSocket.Event>{
        val TAG = "ScarletWSActivity subs"
        override fun onSubscribe(s: Subscription?) {
            Log.d(TAG, "onSubscribe: "+ s?.toString() ?: "NULL")
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete")
        }

        override fun onError(throwable: Throwable) {
            Log.d(TAG, "onError: "+throwable.toString())
        }

        override fun onNext(data: WebSocket.Event) {
            Log.d(TAG, "onNext: "+data.toString())
        }
    }

}
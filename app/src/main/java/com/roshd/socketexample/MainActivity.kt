package com.roshd.socketexample

import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.roshd.socketexample.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {
    var button: Button? = null
    var editText: EditText? = null
    var textView: TextView? = null
    private var clientSocket: Socket? = null
    private var socketIn: BufferedReader? = null
    private var socketOut: PrintWriter? = null
    private val port = 8004
    private val ip = "192.168.1.6"
    private var myHandler: MyHandler? = null
    private var myThread: MyThread? = null

    private val TAG = "MainActivity TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            clientSocket = Socket(ip, port)
            Log.i(TAG, "Client socket: "+clientSocket.toString())
            socketIn =
                BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
            socketOut = PrintWriter(clientSocket!!.getOutputStream(), false)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            e.printStackTrace()
        }

        myHandler = MyHandler(Looper.getMainLooper())
        myThread = MyThread()
        myThread!!.start()
        button = findViewById<View>(R.id.sendBtn) as Button
        textView = findViewById<View>(R.id.receivedTextView) as TextView

        button!!.setOnClickListener {
            Log.i(TAG, "Button clicked")
            editText = findViewById<View>(R.id.sendEditText) as EditText
            val toSend = editText!!.text.toString()
            socketOut!!.print(toSend)
            socketOut!!.flush()
            Log.i(TAG, "Text sent: $toSend")
        }
    }

    internal inner class MyThread : Thread() {
        override fun run() {
            while (true) {
                try {
                    Log.i(TAG,"Trying to read...")

                    val data = socketIn!!.readLine()
                    Log.i(TAG, "Input data: $data")

                    val msg = myHandler!!.obtainMessage()
                    msg.obj = data
                    myHandler!!.sendMessage(msg)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    e.printStackTrace()
                }
            }
        }
    }

    internal inner class MyHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            textView!!.text = textView!!.text.toString() + msg.obj.toString() + "\n"
        }
    }
} 
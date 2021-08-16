package com.roshd.socketexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    private var socket: Socket? = null


    companion object {
        private const val SERVERPORT = 8004
        private const val SERVER_IP = "192.168.1.6"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendBtn = findViewById<View>(R.id.sendBtn) as Button

        sendBtn.setOnClickListener {
            onClick(it)
        }

        Thread(ClientThread()).start()
    }

    fun onClick(view: View?) {
        Log.e("ASD", "HERE!")
        try {
            socket = Socket(SERVER_IP, SERVERPORT)
            val fromClient = findViewById<View>(R.id.sendEditText) as EditText
            val clientMessage = fromClient.text.toString()
            val toServer = PrintWriter(socket!!.getOutputStream(), true)

            toServer.write(clientMessage)
            toServer.close()

        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    internal inner class ClientThread : Runnable {
        override fun run() {
            try {
                val serverAddr: InetAddress =
                    InetAddress.getByName(SERVER_IP)
                socket = Socket(serverAddr, SERVERPORT)
                val inputStreamReader = InputStreamReader(socket!!.getInputStream())
                val receivedTv = findViewById<View>(R.id.receivedTextView) as TextView
                Log.e("Here", "ye")
                val text = BufferedReader(inputStreamReader).readText()
                Log.e("Text", text)
                receivedTv.text = text
                inputStreamReader.close()

            } catch (e1: UnknownHostException) {
                Log.e("Here","e1")
                e1.printStackTrace()
            } catch (e1: IOException) {
                Log.e("Here","e2")
                e1.printStackTrace()
            }
        }
    }

}
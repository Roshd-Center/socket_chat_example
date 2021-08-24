package com.roshd.socketexample.data

import androidx.lifecycle.LiveData
import com.roshd.socketexample.data.models.Message
import com.tinder.scarlet.Stream
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel

interface ChatService {
    @Send
    fun sendMessage(message: Message)

    @Receive
    fun observeMessage(): ReceiveChannel<Message>

    @Receive
    fun observeOnConnectionOpenedEvent(): Stream<WebSocket.Event>
}
val chatService = scarlet.create<ChatService>()
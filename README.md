# socket_chat_example
Testing diffrent ways to connecting to a Socket/WebSocket from an Android client.

Language: Kotlin

## Phase 1: Socket connecting
Multi threaded implementation of simple socket client, using java Socket.

**Activity:** SocketActivity  
**Server:** https://github.com/Roshd-Center/python_socket_chat  
**Highlight Technologies:**
- java.net.Socket




## Phase 2: WebSocket using Ktor
An Android client implementation using [ktor](https://ktor.io/) and [Kotlin coroutines](https://kotlinlang.org/docs/coroutines-overview.html).

**Activity:** KtorWSActivity  
**Server:** https://github.com/Roshd-Center/django_ws_chat  
**Highlight Technologies:**
- Ktor: WebSocket client
- Kotlin Coroutines: Async & non-blocking development
- Klaxon: Lightweight JSON parser for kotlin
- OkHttp: Http client


## Phase 3: WebSocket using Scarlet
An Android client implementation  using [scarlet](https://github.com/Tinder/Scarlet) and [Kotlin coroutines](https://kotlinlang.org/docs/coroutines-overview.html).

**Activity:** ScarletWSActivity  
**Server:** https://github.com/Roshd-Center/django_ws_chat  
**Highlight Technologies:**
- Scarlet: android Websocketing framework
- Kotlin Coroutines: Async & non-blocking development
- Moshi: JSON parser.
- OkHttp: Http client



# How to run
Change the **launcher activity** from `AndroidManifest.xml`:
```xml
<manifest >
  
    ...
    
    <application
        ...
        ...>
        
        <activity
            android:name=".YOUR_TARGET_ACTIVITY"
            ...
        </activity>
    </application>
```


# References:
- https://github.com/Tinder/Scarlet/blob/main/scarlet-lifecycle-android/src/main/java/com/tinder/scarlet/lifecycle/android/ServiceStartedLifecycle.kt
- https://github.com/Tinder/Scarlet/blob/main/scarlet-stream-adapter-coroutines/src/test/java/com/tinder/scarlet/streamadapter/coroutines/ReceiveChannelTest.kt
- https://medium.com/tinder-engineering/taming-websocket-with-scarlet-f01125427677
- ...

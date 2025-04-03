package han.swa.herkansing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class Node(private val port: Int, private val endpointPort: Int? = null) {


    fun start() {
        val serverSocket = ServerSocket(port)
        println("Node started. Listening on port $port...")

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val clientSocket: Socket = serverSocket.accept()
                val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val message = json.decodeFromString<Message>(input.readLine())

                println("node $port has received message: $message.")
                when (message) {
                    is MessageContent -> if (endpointPort != null) forwardMessage(message, endpointPort)
                    is RelayMessage -> forwardMessage(message.message, message.forwardPort)
                }
                clientSocket.close()
            }
        }
    }

}
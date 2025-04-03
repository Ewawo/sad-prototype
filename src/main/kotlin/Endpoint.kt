package han.swa.herkansing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class Endpoint(private val port: Int, private val nodePort: Int) {
    fun sendMessage() {
        print("Enter your message: ")
        val message = readln()
        val unionMessage = buildMessage(message)
        forwardMessage(unionMessage, nodePort)
    }

    fun startMessageReceiver(): Endpoint {
        val serverSocket = ServerSocket(port)
        println("Endpoint started. Listening on port $port...")

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val clientSocket: Socket = serverSocket.accept()
                val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val message = json.decodeFromString<Message>(input.readLine())

                when (message) {
                    is MessageContent -> {
                        println("Endpoint on port $port has received message: ${message.textMessage}.")
                        clientSocket.close()
                    }
                    else -> println("Endpoint on port $port has received a message of unsupported format")
                }
            }
        }
        return this
    }

    private fun buildMessage(message: String): Message {
        val messageContent = MessageContent(message)

        // hardcoded nodes for now, later you would calculate a path through the supplied graph from the master server
        return RelayMessage(RelayMessage(RelayMessage(messageContent, if (port == 9000) 8000 else 8003), 8001), 8002)
    }
}
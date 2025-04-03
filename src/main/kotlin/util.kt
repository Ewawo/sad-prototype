package han.swa.herkansing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.net.Socket

val json = Json {
    serializersModule = SerializersModule {
        polymorphic(Message::class) {
            subclass(MessageContent::class)
            subclass(RelayMessage::class)
        }
    }
}

fun forwardMessage(message: Message, port: Int) {
    CoroutineScope(Dispatchers.IO).launch {
        Socket("localhost", port).use { socket ->
            val output = socket.getOutputStream().bufferedWriter()
            val jsonString = json.encodeToString(message)
            output.write(jsonString)
            output.newLine()
            output.flush()
        }
    }
}

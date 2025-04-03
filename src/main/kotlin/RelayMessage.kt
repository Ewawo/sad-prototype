package han.swa.herkansing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("relayMessage")
data class RelayMessage(val message: Message, val forwardPort: Int): Message
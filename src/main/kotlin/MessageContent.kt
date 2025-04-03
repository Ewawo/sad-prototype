package han.swa.herkansing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("messageContext")
data class MessageContent(val textMessage: String): Message
package kz.post.jcourier.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kz.post.jcourier.app.BOT
import kz.post.jcourier.app.USER

data class ChatModel(val value: String, val type: String, val from: String)

var chat_list: SnapshotStateList<ChatModel> = mutableStateListOf(
    ChatModel("Hello", "text", "bot"),
    ChatModel("Dear", "text", "bot"),
    ChatModel("How Are you?", "text", "bot"),
    ChatModel("I am good", "text", "user"),
    ChatModel("One", "text", kz.post.jcourier.app.BOT),
    ChatModel("Two", "text", kz.post.jcourier.app.USER),
    ChatModel("Three", "text", kz.post.jcourier.app.USER),
    ChatModel("Four", "text", kz.post.jcourier.app.BOT),
)
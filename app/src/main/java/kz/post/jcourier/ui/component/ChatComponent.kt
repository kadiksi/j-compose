import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.post.jcourier.data.model.ChatModel


object Ui {
    @Composable
    fun ChatBubble(chatModel: ChatModel) {
        val left = chatModel.from == kz.post.jcourier.app.USER;
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = if (left) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.End,
                    text = chatModel.value
                )
            }
        }
    }
}



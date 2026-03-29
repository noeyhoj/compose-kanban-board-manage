package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CreateAlertSnackBar(modifier: Modifier = Modifier, text: String = "", onClick: () -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text, color = Color.White)
        IconButton(onClick = onClick) {
            Icon(Icons.Default.Close, contentDescription = "닫기 버튼", tint = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateAlertSnackBarPreview() {
    CreateAlertSnackBar(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = Color(0xFF322F35))
            .padding(start = 16.dp)
            .size(width = 344.dp, height = 48.dp),
        text = "새로운 태스크가 추가되었습니다.",
        onClick = { },
    )
}

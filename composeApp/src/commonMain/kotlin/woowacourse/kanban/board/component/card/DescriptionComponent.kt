package woowacourse.kanban.board.component.card

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.constant.CONTENT_COLOR

@Composable
fun DescriptionComponent(description: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        Text(
            description,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            fontSize = 14.sp,
            color = Color(CONTENT_COLOR),
        )
    }
}

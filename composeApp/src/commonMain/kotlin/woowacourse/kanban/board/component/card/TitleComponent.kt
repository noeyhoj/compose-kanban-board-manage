package woowacourse.kanban.board.component.card

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TitleComponent(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        Text(
            title,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

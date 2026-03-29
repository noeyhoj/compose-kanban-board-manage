package woowacourse.kanban.board.component.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.model.Tag

@Composable
fun TagComponent(tag: Tag, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        Text(tag.text, modifier = Modifier.padding(6.dp), fontSize = 10.sp)
    }
}

package woowacourse.kanban.board.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.constant.PROFILE_BG_COLOR
import woowacourse.kanban.board.constant.PROFILE_COLOR

@Composable
fun ProfileComponent(nickname: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color(PROFILE_COLOR),
            modifier = Modifier.size(25.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = Color(PROFILE_BG_COLOR))
                .background(color = Color(PROFILE_BG_COLOR)),
        )
        Text(
            nickname,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

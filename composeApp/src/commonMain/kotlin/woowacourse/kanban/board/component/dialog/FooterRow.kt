package woowacourse.kanban.board.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.constant.CREATE_BG
import woowacourse.kanban.board.constant.CREATE_BG_ERROR
import woowacourse.kanban.board.constant.PRIMARY_TEXT

@Composable
fun FooterRow(onCancel: () -> Unit, onCreate: () -> Unit, isCreateError: Boolean, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        FooterButton(
            modifier = Modifier,
            text = "취소",
            backgroundColor = Color.White,
            textColor = Color(PRIMARY_TEXT),
            onClick = onCancel,
        )
        Spacer(modifier = Modifier.width(12.dp))
        FooterButton(
            modifier = Modifier,
            text = "생성",
            textColor = Color.White,
            backgroundColor = if (!isCreateError) Color(CREATE_BG) else Color(CREATE_BG_ERROR),
            onClick = onCreate,
            enabled = !isCreateError,
        )
    }
}

@Composable
private fun FooterButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(
                width = 68.dp,
                height = 44.dp,
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .background(backgroundColor).clickable(onClick = onClick, enabled = enabled),
    ) {
        Text(text, color = textColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun CancelFooterButtonPreview() {
    FooterButton(
        modifier = Modifier,
        text = "취소",
        backgroundColor = Color.White,
        textColor = Color(PRIMARY_TEXT),
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun CreateEnableFooterButtonPreview() {
    FooterButton(
        modifier = Modifier,
        text = "생성",
        textColor = Color.White,
        backgroundColor = Color(CREATE_BG),
        onClick = {},
        enabled = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun CreateNotEnableFooterButtonPreview() {
    FooterButton(
        modifier = Modifier,
        text = "생성",
        textColor = Color.White,
        backgroundColor = Color(CREATE_BG_ERROR),
        onClick = {},
        enabled = false,
    )
}

@Preview(showBackground = true)
@Composable
private fun CreateEnableFooterRowPreview() {
    FooterRow(
        onCancel = {},
        onCreate = {},
        isCreateError = false,
    )
}

@Preview(showBackground = true)
@Composable
private fun CreateNotEnableFooterRowPreview() {
    FooterRow(
        onCancel = {},
        onCreate = {},
        isCreateError = true,
    )
}

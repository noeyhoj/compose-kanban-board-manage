package woowacourse.kanban.board.component.projectManage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ProjectSideBar(modifier: Modifier = Modifier, projectTitle: String = "", subTitle: String = "", content: @Composable () -> Unit) {
    Column(
        modifier = modifier.fillMaxHeight().width(256.dp).background(color = Color.White),
    ) {
        ProjectTitle(modifier = Modifier.padding(24.dp), projectTitle = projectTitle, subTitle = subTitle)
        HorizontalDivider()
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            content()
        }
    }
}

@Composable
private fun ProjectTitle(modifier: Modifier = Modifier, projectTitle: String = "", subTitle: String = "") {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(projectTitle, fontSize = 18.sp, fontWeight = FontWeight.W600)
        Box(modifier = Modifier.height(4.dp))
        Text(subTitle, fontSize = 14.sp, fontWeight = FontWeight.W400, color = Color(0xFF6a7282))
    }
}

@Preview(showBackground = true)
@Composable
private fun ProjectSideBarPreview() {
    ProjectSideBar(
        projectTitle = "프로젝트",
        subTitle = "4주차 미션 보드",
        content = {},
    )
}

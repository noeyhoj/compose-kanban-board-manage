package woowacourse.kanban.board.study

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.planet
import org.jetbrains.compose.resources.painterResource

@Composable
fun CheckerScreen() {
    var checked by remember { mutableStateOf(true) }

    CheckerView(checked = checked) {
        checked = !checked
    }
}

@Composable
fun CheckerView(checked: Boolean, check: () -> Unit) {
    Column {
        Checkbox(
            checked = checked,
            onCheckedChange = { check() },
        )
        if (checked) Text(text = "체크됨!!!")
    }
}

// 1. comp
// 2. 함수에서 option + enter
// 3. 직접 작성
// option + command + l -> 코드 깔끔하게

// 텍스트 예제
@Composable
@Preview(showBackground = true)
fun TextExample() {
    Text(text = "Jetpack Compose Text 실습", fontSize = 22.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
}

@Composable
@Preview(showBackground = true)
fun ImageExample() {
    Image(painter = painterResource(Res.drawable.planet), contentDescription = "행성이 사진")
}

@Composable
@Preview(showBackground = true)
fun IconExample() {
    Icon(imageVector = Icons.Default.Favorite, contentDescription = "기본 하트")
}

@Composable
@Preview(showBackground = true)
fun ButtonExample1() {
    Button(
        onClick = {
            println("버튼 클릭")
        },
    ) {
        Text("버튼")
    }
}

@Composable
@Preview(showBackground = true)
fun ButtonExample2() {
    Button(
        onClick = {
            println("버튼 클릭")
        },
    ) {
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "아이콘 버튼의 하트 아이콘")
    }
}

@Composable
@Preview(showBackground = true)
fun ButtonExample3() {
    Button(
        onClick = {
            println("버튼 클릭")
        },
    ) {
        Row {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "아이콘 버튼의 하트 아이콘")
            Text("좋아요")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BoxExample() {
    Box(
        modifier = Modifier.size(200.dp),
    ) {
        Box(
            modifier = Modifier.size(50.dp).background(color = Color.Red).align(Alignment.TopStart),
        )
        Box(
            modifier = Modifier.size(50.dp).background(color = Color.Yellow).align(Alignment.Center),
        )
        Box(
            modifier = Modifier.size(50.dp).background(color = Color.Green).align(Alignment.BottomEnd),
        )
    }
}

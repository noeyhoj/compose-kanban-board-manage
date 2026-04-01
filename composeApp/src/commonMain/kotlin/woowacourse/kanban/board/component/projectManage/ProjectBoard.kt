package woowacourse.kanban.board.component.projectManage

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.component.kanbanBoard.KanbanBoard
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.KanbanBoardData
import woowacourse.kanban.board.model.ProjectData
import woowacourse.kanban.board.model.Status

@Composable
fun ProjectBoard(
    projectData: ProjectData,
    addBoardData: (KanbanBoardData, BoardData) -> Unit,
    editBoardData: (KanbanBoardData, BoardData) -> Unit,
    deleteBoardData: (KanbanBoardData, BoardData) -> Unit,
    moveBoardDataStatus: (KanbanBoardData, BoardData, Status) -> Unit,
) {

    var selectedIndex by remember { mutableStateOf(0) }

    val selectedKanbanBoardData = projectData.getIndexingKanbanBoardData(selectedIndex)

    fun selectedOnValueChange(kanbanBoardData: KanbanBoardData): Int {
        return projectData.getIndex(kanbanBoardData = kanbanBoardData)
    }

    Row {
        ProjectSideBar(
            projectTitle = "프로젝트",
            subTitle = "4주차 미션 보드",
        ) {
            projectData.kanbanBoardDatas.forEach { kanbanBoardData ->
                KanbanBoardButton(
                    title = kanbanBoardData.title,
                    onClick = { selectedIndex = selectedOnValueChange(kanbanBoardData) },
                    isSelected = selectedKanbanBoardData == kanbanBoardData,
                )
            }
        }
        VerticalDivider()
        KanbanBoard(
            kanbanBoardData = selectedKanbanBoardData,
            onAddBoardData = { boardData ->
                addBoardData(selectedKanbanBoardData, boardData)
            },
            onEditBoardData = { boardData ->
                editBoardData(selectedKanbanBoardData, boardData)
                println("KanbanBoard에서 edit했을 때 : ${selectedKanbanBoardData.boardList}")
            },
            onDeleteBoardData = { boardData ->
                deleteBoardData(selectedKanbanBoardData, boardData)
            },
            onMoveBoardDataStatus = { task, targetStatus ->
                moveBoardDataStatus(selectedKanbanBoardData, task, targetStatus)
            },
        )
    }
}

@Composable
private fun KanbanBoardButton(modifier: Modifier = Modifier, title: String = "", isSelected: Boolean = false, onClick: () -> Unit = {}) {
    Button(
        modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth(),
        colors = ButtonColors(
            containerColor = if (isSelected) Color(0xFFEEF2FF) else Color.White,
            contentColor = Color.White,
            disabledContainerColor = if (isSelected) Color(0xFFEEF2FF) else Color.White,
            disabledContentColor = Color.White,
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
    ) {
        Text(
            text = title,
            color = if (isSelected) Color(0xFF432DD7) else Color(0xFF364153),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProjectBoardPreview() {
    var projectData by remember {
        mutableStateOf(
            ProjectData(kanbanBoardDatas = ProjectData.defaultKanbanBoardDatas),
        )
    }

    ProjectBoard(
        projectData = projectData,
        addBoardData = { selectedKanbanBoardData, boardData ->
            projectData = projectData.addBoardData(selectedKanbanBoardData = selectedKanbanBoardData, boardData = boardData)
        },
        editBoardData = { selectedKanbanBoardData, boardData ->
            projectData = projectData.editBoardData(selectedKanbanBoardData, boardData)
            println("수정 : ${projectData.kanbanBoardDatas[0]}")
        },
        deleteBoardData = { selectedKanbanBoardData, boardData ->
            projectData = projectData.deleteBoardData(selectedKanbanBoardData, boardData)
        },
        moveBoardDataStatus = { selectedKanbanBoardData, task, targetStatus ->
            println("움직이기 전 : ${projectData.kanbanBoardDatas[0]}")
            projectData = projectData.moveBoardDataStatus(selectedKanbanBoardData, task, targetStatus)
            println("움직임 : ${projectData.kanbanBoardDatas[0]}")
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun KanbanBoardButtonPreview() {
    KanbanBoardButton(
        title = "Compose1", isSelected = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun KanbanBoardButtonLongTitlePreview() {
    KanbanBoardButton(
        title = "Compose3너무너무너무너무긴제목", isSelected = false,
    )
}

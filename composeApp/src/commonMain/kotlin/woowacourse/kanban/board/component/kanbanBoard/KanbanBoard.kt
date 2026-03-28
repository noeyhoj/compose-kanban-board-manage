package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import woowacourse.kanban.board.component.dialog.TaskCreateDialog
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.KanbanBoardData
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.StatusColor
import woowacourse.kanban.board.model.Tag
import woowacourse.kanban.board.state.BoardDataState

@Composable
fun KanbanBoard(
    kanbanBoardData: KanbanBoardData,
    modifier: Modifier = Modifier,
    onAddBoardData: (BoardData) -> Unit = {},
    onMoveBoardDataStatus: (BoardData, Status) -> Unit = { _, _ -> },
) {
    val statuses = remember { Status.entries }

    val names = remember { listOf("다이노", "페임스") }

    var showDialog by remember { mutableStateOf(false) }
    var isShowSnackBar by remember { mutableStateOf(false) }

    var text by remember { mutableStateOf("새로운 태스크가 생성되었습니다.") }

    fun onCreateClick() {
        showDialog = true
    }

    fun onDismissRequest() {
        showDialog = false
    }

    fun onShowSnackBar() {
        isShowSnackBar = true
    }

    fun onTaskCreate(boardDataState: BoardDataState) {
        val boardData = BoardData(
            title = boardDataState.titleInputValue,
            description = boardDataState.descriptionInputValue,
            tags = if (boardDataState.tagsInputValue.isNotBlank()) {
                boardDataState.tagsInputValue.split(",").map { Tag(it) }
            } else emptyList(),
            status = boardDataState.statusValue,
            nickname = boardDataState.nameValue,
        )
        onAddBoardData(boardData)
    }

    suspend fun showSnackBar() {
        delay(3000.milliseconds)
        isShowSnackBar = false
    }

    fun onSnackBarCancelClick() {
        isShowSnackBar = false
    }

    var draggedTask by remember { mutableStateOf<BoardData?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<Status, Rect>() }

    Box {
        Column(
            modifier = modifier.fillMaxSize().background(color = Color.White),
        ) {
            KanbanBoardTitleBar(
                title = kanbanBoardData.title,
                progress = kanbanBoardData.progress(),
                doneCount = kanbanBoardData.doneCount(),
                totalStatusCount = kanbanBoardData.totalStatusCount(),
                onCreateClick = { onCreateClick() },
            )
            Row(
                modifier = Modifier.padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Status.entries.forEach { status ->
                    StatusCardManageBox(
                        boardList = kanbanBoardData.getStatusBoard(status),
                        status = status,
                        statusColor = StatusColor.getStatusColor(status),
                        getIsDropTarget = {
                            currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
                        },
                        onBoundsChanged = { rect -> columnBounds[status] = rect },
                        onTaskDragStart = { task -> draggedTask = task },
                        onTaskDragChange = { pos -> currentDragPosition = pos },
                        onTaskDragEnd = {
                            val dropPosition = currentDragPosition ?: return@StatusCardManageBox
                            val targetStatus = columnBounds.entries
                                .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                            draggedTask?.let { task ->
                                if (targetStatus != null && task.status != targetStatus) {
                                    onMoveBoardDataStatus(task, targetStatus)
                                    text = "태스크가 이동되었습니다."
                                    isShowSnackBar = true
                                }
                            }
                            currentDragPosition = null
                            draggedTask = null
                        },
                        onTaskDragCancel = {
                            currentDragPosition = null
                            draggedTask = null
                        },
                    )
                }
            }

            if (showDialog) {
                Dialog(
                    onDismissRequest = { onDismissRequest() },
                ) {
                    TaskCreateDialog(
                        statuses = statuses,
                        names = names,
                        onTaskCreate = {
                            onTaskCreate(it)
                            onDismissRequest()
                            text = "새로운 태스크가 생성되었습니다."
                            onShowSnackBar()
                        },
                        onDismissRequest = { onDismissRequest() },
                    )
                }
            }
        }
        LaunchedEffect(isShowSnackBar) {
            if (isShowSnackBar) showSnackBar()
        }
        if (isShowSnackBar) CreateAlertSnackBar(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
                .background(color = Color(0xFF322F35))
                .padding(start = 16.dp)
                .size(width = 344.dp, height = 48.dp)
                .align(alignment = Alignment.BottomCenter),
            text = text,
            onClick = { onSnackBarCancelClick() },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun KanbanBoardPreview() {
    KanbanBoard(kanbanBoardData = KanbanBoardData(title = "Compose1"))
}

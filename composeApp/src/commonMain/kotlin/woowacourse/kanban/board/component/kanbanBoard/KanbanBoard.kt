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
import woowacourse.kanban.board.component.dialog.TaskEditDialog
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.KanbanBoardData
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.StatusColor
import woowacourse.kanban.board.state.BoardDataState
import woowacourse.kanban.board.state.KanbanBoardState
import kotlin.io.path.Path

@Composable
fun KanbanBoard(
    kanbanBoardData: KanbanBoardData,
    modifier: Modifier = Modifier,
    onAddBoardData: (BoardData) -> Unit = {},
    onEditBoardData: (BoardData) -> Unit = {},
    onDeleteBoardData: (BoardData) -> Unit = {},
    onMoveBoardDataStatus: (BoardData, Status) -> Unit = { _, _ -> },
) {
    val kanbanBoardState = remember { KanbanBoardState() }

    Box {
        Column(
            modifier = modifier.fillMaxSize().background(color = Color.White),
        ) {
            KanbanBoardTitleBar(
                title = kanbanBoardData.title,
                progress = kanbanBoardData.progress(),
                doneCount = kanbanBoardData.doneCount(),
                totalStatusCount = kanbanBoardData.totalStatusCount(),
                onCreateClick = { kanbanBoardState.onCreateClick() },
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
                        getIsDropTarget = { kanbanBoardState.getIsDropTarget(status) },
                        onBoundsChanged = { rect -> kanbanBoardState.onBoundsChanged(rect, status) },
                        onTaskDragStart = { task -> kanbanBoardState.onTaskDragStart(task) },
                        onTaskDragChange = { pos -> kanbanBoardState.onTaskDragChange(pos) },
                        onTaskDragEnd = {
                            kanbanBoardState.onTaskDragEnd(onMoveBoardDataStatus)
                        },
                        onTaskDragCancel = { kanbanBoardState.onTaskDragCancel() },
                        onClick = { boardData -> kanbanBoardState.onCardClick(boardData) },
                    )
                }
            }

            if (kanbanBoardState.showDialog) {
                Dialog(
                    onDismissRequest = { kanbanBoardState.onDismissRequest() },
                ) {
                    TaskCreateDialog(
                        boardDataState = BoardDataState(),
                        title = "새 태스크 생성",
                        onTaskCreate = {
                            onAddBoardData(it)
                            kanbanBoardState.onTaskCreate()
                        },
                        onDismissRequest = { kanbanBoardState.onDismissRequest() },
                    )
                }
            }

            if (kanbanBoardState.editDialog) {
                Dialog(
                    onDismissRequest = { kanbanBoardState.editDialog = false },
                ) {
                    TaskEditDialog(
                        title = "기존 태스크 수정",
                        onEditTask = { boardData ->
                            onEditBoardData(boardData)
                            kanbanBoardState.onEditTask()
                        },
                        onDeleteTask = { boardData ->
                            if (boardData.status != Status.REVIEW && boardData.status != Status.DONE) {
                                onDeleteBoardData(boardData)
                                kanbanBoardState.onDeleteTask()
                            } else {
                                kanbanBoardState.onNotDeleteTask()
                            }
                        },
                        onDismissRequest = { kanbanBoardState.editDialog = false },
                        boardDataState = kanbanBoardState.boardDataState,
                    )
                }
            }
        }
        LaunchedEffect(kanbanBoardState.isShowSnackBar) {
            if (kanbanBoardState.isShowSnackBar) kanbanBoardState.showSnackBar()
        }
        if (kanbanBoardState.isShowSnackBar) CreateAlertSnackBar(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
                .background(color = Color(0xFF322F35))
                .padding(start = 16.dp)
                .size(width = 344.dp, height = 48.dp)
                .align(alignment = Alignment.BottomCenter),
            text = kanbanBoardState.text,
            onClick = { kanbanBoardState.onSnackBarCancelClick() },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun KanbanBoardPreview() {
    KanbanBoard(kanbanBoardData = KanbanBoardData(title = "Compose1"))
}

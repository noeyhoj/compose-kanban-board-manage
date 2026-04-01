package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import kotlinx.coroutines.delay
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.KanbanBoardData
import woowacourse.kanban.board.model.MoveStatus
import woowacourse.kanban.board.model.Status
import kotlin.collections.get
import kotlin.time.Duration.Companion.milliseconds

class KanbanBoardState {

    var showDialog by mutableStateOf(false)
    var editDialog by mutableStateOf(false)
    var isShowSnackBar by mutableStateOf(false)

    var text by mutableStateOf("새로운 태스크가 생성되었습니다.")

    var draggedTask by mutableStateOf<BoardData?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<Status, Rect>()

    var boardDataState by mutableStateOf(BoardDataState())

    fun onCreateClick() {
        showDialog = true
    }

    fun onTaskCreate() {
        onDismissRequest()
        text = "새로운 태스크가 생성되었습니다."
        onShowSnackBar()
    }

    fun onEditTask() {
        editDialog = false
        text = "태스크가 수정되었습니다."
        onShowSnackBar()
    }

    fun onDeleteTask() {
        editDialog = false
        text = "태스크가 삭제되었습니다."
        onShowSnackBar()
    }

    fun onNotDeleteTask() {
        editDialog = false
        text = "해당 상태에서는 태스크 삭제가 불가합니다."
        onShowSnackBar()
    }

    fun onDismissRequest() {
        showDialog = false
    }

    fun onShowSnackBar() {
        isShowSnackBar = true
    }

    suspend fun showSnackBar() {
        delay(3000.milliseconds)
        isShowSnackBar = false
    }

    fun onSnackBarCancelClick() {
        isShowSnackBar = false
    }

    fun getIsDropTarget(status: Status): Boolean {
        return currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
    }

    fun onBoundsChanged(rect: Rect, status: Status) { columnBounds[status] = rect }

    fun onTaskDragStart(task: BoardData) { draggedTask = task }

    fun onTaskDragChange(pos: Offset) { currentDragPosition = pos }

    fun onTaskDragEnd(onMoveBoardDataStatus: (BoardData, Status) -> Unit) {
        val dropPosition = currentDragPosition ?: return
        val targetStatus = columnBounds.entries
            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

        draggedTask?.let { task ->
            if (targetStatus != null && task.status != targetStatus) {
                when(MoveStatus.getMoveStatus(task, targetStatus)) {
                    MoveStatus.SUCCESS -> {
                        onMoveBoardDataStatus(task, targetStatus)
                        text = "태스크가 이동되었습니다."
                    }
                    MoveStatus.FAILED -> {
                        text = "해당 상태로 옮길 수 없습니다."
                    }
                    MoveStatus.IN_PROGRESS -> {
                        text = "담당자를 지정해야 상태를 옮길 수 있습니다."
                    }
                }
                isShowSnackBar = true
            }
        }
        currentDragPosition = null
        draggedTask = null
    }

    fun onTaskDragCancel() {
        currentDragPosition = null
        draggedTask = null
    }

    fun onCardClick(boardData: BoardData) {
        boardDataState = BoardDataState(
            id = boardData.id,
            title = boardData.title,
            description = boardData.description,
            tags = boardData.tags.map { it.text }.joinToString(","),
            status = boardData.status,
            name = boardData.nickname,
        )
        editDialog = true
    }
}

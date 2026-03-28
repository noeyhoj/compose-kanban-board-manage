package woowacourse.kanban.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.component.projectManage.ProjectBoard
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.KanbanBoardData
import woowacourse.kanban.board.model.ProjectData
import woowacourse.kanban.board.model.Status

@Composable
fun App() {
    var projectData by remember {
        mutableStateOf(
            ProjectData(),
        )
    }

    fun onAddBoardData(selectedKanbanBoardData: KanbanBoardData, boardData: BoardData) {
        projectData = projectData.onAddBoardData(selectedKanbanBoardData, boardData)
    }

    fun onMoveBoardDataStatus(selectedKanbanBoardData: KanbanBoardData, task: BoardData, targetStatus: Status) {
        projectData = projectData.onMoveBoardDataStatus(selectedKanbanBoardData, task, targetStatus)
    }

    ProjectBoard(
        projectData = projectData,
        onAddBoardData = { selectedKanbanBoardData, boardData ->
            onAddBoardData(selectedKanbanBoardData, boardData)
        },
        onMoveBoardDataStatus = { selectedKanbanBoardData, task, targetStatus ->
            onMoveBoardDataStatus(selectedKanbanBoardData, task, targetStatus)
        },
    )
}

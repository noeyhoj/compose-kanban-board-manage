package woowacourse.kanban.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import woowacourse.kanban.board.component.projectManage.ProjectBoard
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.KanbanBoardData
import woowacourse.kanban.board.model.ProjectData
import woowacourse.kanban.board.model.Status

@Composable
fun App() {
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
        moveBoardDataStatus = { selectedKanbanBoardData, task, targetStatus ->
            projectData = projectData.moveBoardDataStatus(selectedKanbanBoardData, task, targetStatus)
        },
    )
}

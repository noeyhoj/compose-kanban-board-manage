package woowacourse.kanban.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.component.projectManage.ProjectBoard
import woowacourse.kanban.board.model.ProjectData

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
        editBoardData = { selectedKanbanBoardData, boardData ->
            projectData = projectData.editBoardData(selectedKanbanBoardData, boardData)
            println("수정 : ${projectData.kanbanBoardDatas[0]}")
        },
        deleteBoardData = { selectedKanbanBoardData, boardData ->
            projectData = projectData.deleteBoardData(selectedKanbanBoardData, boardData)
        },
        moveBoardDataStatus = { selectedKanbanBoardData, task, targetStatus ->
            projectData = projectData.moveBoardDataStatus(selectedKanbanBoardData, task, targetStatus)
            println("움직임 : ${projectData.kanbanBoardDatas[0]}")
        }
    )
}

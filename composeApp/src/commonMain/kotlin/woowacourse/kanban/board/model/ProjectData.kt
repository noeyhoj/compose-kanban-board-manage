package woowacourse.kanban.board.model

data class ProjectData(
    val kanbanBoardDatas: List<KanbanBoardData> = emptyList(),
) {
    fun selectedOnValueChange(kanbanBoardData: KanbanBoardData): Int {
        return kanbanBoardDatas.indexOfLast { it.id == kanbanBoardData.id }
    }

    fun onAddBoardData(selectedKanbanBoardData: KanbanBoardData, boardData: BoardData): ProjectData {
        return copy(
            kanbanBoardDatas = kanbanBoardDatas.map {
                if (it.id == selectedKanbanBoardData.id) it.addBoardData(boardData) else it
            },
        )
    }

    fun onMoveBoardDataStatus(selectedKanbanBoardData: KanbanBoardData, task: BoardData, targetStatus: Status): ProjectData {
        return copy(
            kanbanBoardDatas = kanbanBoardDatas.map {
                if (it.id == selectedKanbanBoardData.id) it.moveBoardDataStatus(task, targetStatus) else it
            },
        )
    }

    companion object {
        val defaultKanbanBoardDatas = listOf(
            KanbanBoardData(title = "Compose1"),
            KanbanBoardData(title = "Compose2"),
            KanbanBoardData(title = "Compose3너무너무너무너무너무너무너무너무"),
        )
    }
}

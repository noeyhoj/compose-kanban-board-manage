package woowacourse.kanban.board.model

import java.util.UUID

data class KanbanBoardData(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val boardList: List<BoardData> = emptyList(),
) {
    fun totalStatusCount(): Int = boardList.size
    fun doneCount(): Int = boardList.count { it.status == Status.DONE }

    fun progress(): Float =
        if (totalStatusCount() == 0) 0f else (boardList.count { it.status == Status.DONE }).toFloat() / totalStatusCount()

    fun addBoardData(boardData: BoardData): KanbanBoardData = copy(boardList = boardList + boardData)

    fun moveBoardDataStatus(task: BoardData, targetStatus: Status): KanbanBoardData {
        return copy(
            boardList = boardList.map { boardData ->
                if (boardData.id == task.id) boardData.copy(status = targetStatus) else boardData
            },
        )
    }

    fun getStatusBoard(status: Status): List<BoardData> = boardList.filter { it.status == status }
}

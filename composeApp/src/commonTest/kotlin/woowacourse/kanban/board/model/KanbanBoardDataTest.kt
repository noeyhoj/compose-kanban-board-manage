package woowacourse.kanban.board.model

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.kanban.board.constant.DEFAULT_CONTENT
import woowacourse.kanban.board.constant.DEFAULT_NAME
import woowacourse.kanban.board.constant.DEFAULT_TITLE
import woowacourse.kanban.board.constant.MAX_CONTENT
import woowacourse.kanban.board.constant.MAX_NAME
import woowacourse.kanban.board.constant.MAX_TITLE

class KanbanBoardDataTest {
    private val boardList = listOf(
        BoardData(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            status = Status.IN_PROGRESS,
            nickname = DEFAULT_NAME,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        BoardData(
            title = MAX_TITLE,
            description = MAX_CONTENT,
            tags = listOf(Tag("너무너무"), Tag("긴태그"), Tag("최대로"), Tag("5자까지"), Tag("5개제한임")),
            status = Status.DONE,
            nickname = MAX_NAME,
        ),
    )

    private val kanbanBoardData = KanbanBoardData(
        title = "Compose1", boardList = boardList,
    )

    private val targetCard = kanbanBoardData.boardList[0]

    @Test
    fun `태스크 전체 개수를 알고 있다`() {

        Assertions.assertThat(kanbanBoardData.totalStatusCount()).isEqualTo(boardList.size)
    }

    @Test
    fun `상태(To-Do, In Progress, Done)별 태스크 개수가 노출된다`() {

        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.TODO).size).isEqualTo(3)
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.IN_PROGRESS).size).isEqualTo(1)
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.DONE).size).isEqualTo(1)
    }

    @Test
    fun `5개 중에 done이 1개라면 20%의 완료율을 계산한다`() {

        Assertions.assertThat(kanbanBoardData.progress()).isEqualTo(0.2f)
    }

    @Test
    fun `상태를 To-Do에서 In Progress으로 옮겼을 때 객체의 상태가 변경된다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(task = targetCard, targetStatus = Status.IN_PROGRESS)

        assertThat(changeKanbanBoardData.boardList[0].status).isEqualTo(Status.IN_PROGRESS)
    }

    @Test
    fun `상태를 To-Do에서 Done으로 옮겼을 때 doneCount가 증가한다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(task = targetCard, targetStatus = Status.DONE)

        assertThat(changeKanbanBoardData.doneCount()).isEqualTo(2)
    }

    @Test
    fun `상태를 To-Do에서 In Progress로 변경했을 때 완료율은 변하지 않는다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(task = targetCard, targetStatus = Status.IN_PROGRESS)

        assertThat(changeKanbanBoardData.progress()).isEqualTo(kanbanBoardData.progress())
    }
}

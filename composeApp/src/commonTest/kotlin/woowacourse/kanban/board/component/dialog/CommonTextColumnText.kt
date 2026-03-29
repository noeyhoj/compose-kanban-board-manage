package woowacourse.kanban.board.component.dialog

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.state.BoardDataState

@OptIn(ExperimentalTestApi::class)
class CommonTextColumnText {

    @Test
    fun `텍스트 필드에 입력한 내용이 입력한대로 출력되어야 한다`() = runComposeUiTest {
        val boardDataState = BoardDataState()

        setContent {
            CommonTextColumn(
                title = "제목 *",
                content = boardDataState.titleInputValue,
                onValueChange = { boardDataState.titleOnValueChange(it) },
                placeholderText = "태스크 제목을 입력하세요",
                isError = boardDataState.isTitleError,
            )
        }

        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목입니다")
        assertThat(boardDataState.titleInputValue).isEqualTo("제목입니다")
    }

    @Test
    fun `제목 검증 실패시 에러 표시가 출력되야 한다`() = runComposeUiTest {
        val isTitleError = true

        setContent {
            CommonTextColumn(
                title = "제목 *",
                content = "",
                onValueChange = {},
                placeholderText = "태스크 제목을 입력하세요",
                isError = isTitleError,
            )
        }

        onNodeWithText("이건,,,,올바르지 않은 형식입니다,,,,,,,,,").assertExists()
    }

    @Test
    fun `태그 검증 실패시 에러 표시가 출력되야 한다`() = runComposeUiTest {
        val isTagsError = true

        setContent {
            CommonTextColumn(
                title = "태그",
                content = "",
                onValueChange = {},
                placeholderText = "",
                isError = isTagsError,
                isSupportingText = true,
            )
        }

        onNodeWithText("태그 형식이 올바르지 않습니다.").assertExists()
    }

    @Test
    fun `태그가 다섯글자 이하로 입력되면 입력 값이 텍스트 필드에 존재한다`() = runComposeUiTest {

        val boardDataState = BoardDataState()

        setContent {
            CommonTextColumn(
                title = "태그",
                content = boardDataState.tagsInputValue,
                onValueChange = { boardDataState.tagsOnValueChange(it) },
                placeholderText = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                isError = boardDataState.isTagsError,
                isSupportingText = true,
            )
        }

        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("다섯글자")
        onNodeWithText("다섯글자").assertExists()
    }

    @Test
    fun `태그가 여섯글자 이상 입력되면 오류 메세지가 발생한다`() = runComposeUiTest {

        val boardDataState = BoardDataState()

        setContent {

            CommonTextColumn(
                title = "태그",
                content = boardDataState.tagsInputValue,
                onValueChange = { boardDataState.tagsOnValueChange(it) },
                placeholderText = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                isError = boardDataState.isTagsError,
                isSupportingText = true,
            )
        }

        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("여섯글자이상입력")
        onNodeWithText("태그 형식이 올바르지 않습니다.").assertExists()
    }
}

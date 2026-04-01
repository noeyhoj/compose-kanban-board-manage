package woowacourse.kanban.board.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.state.BoardDataState

@Composable
fun TaskCreateDialog(
    modifier: Modifier = Modifier,
    boardDataState: BoardDataState,
    title: String = "",
    onTaskCreate: (BoardData) -> Unit,
    onDismissRequest: () -> Unit,
) {

    val isCreateError = boardDataState.isTitleError || boardDataState.isTagsError || boardDataState.titleInputValue.isBlank() || (boardDataState.nameValue == "" && boardDataState.statusValue != Status.TODO)

    Column(
        modifier = modifier
            .background(color = Color.White)
            .verticalScroll(rememberScrollState()),
    ) {
        DialogBar(
            modifier = Modifier.padding(
                vertical = 28.dp,
                horizontal = 24.dp,
            )
                .fillMaxWidth(),
            title = title,
            onClick = onDismissRequest,
        )
        HorizontalDivider()
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth(),
                title = "제목 *",
                content = boardDataState.titleInputValue,
                onValueChange = { boardDataState.titleOnValueChange(it) },
                isError = boardDataState.isTitleError,
                placeholderText = "태스크 제목을 입력하세요",
            )
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth().height(116.dp),
                title = "설명",
                content = boardDataState.descriptionInputValue,
                onValueChange = { boardDataState.descriptionOnValueChange(it) },
                placeholderText = "태스크에 대한 자세한 설명을 입력하세요",
            )
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth(),
                title = "태그 *",
                content = boardDataState.tagsInputValue,
                onValueChange = { boardDataState.tagsOnValueChange(it) },
                isError = boardDataState.isTagsError,
                placeholderText = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                isSupportingText = true,
            )
            CommonButtonColumn(
                header = "상태 *",
                items = boardDataState.statuses,
                isSelected = { boardDataState.isSelectedStatus(it) },
                onValueChange = { boardDataState.statusOnValueChange(it) },
            ) { status, isSelected, onClick ->
                StatusButton(status = status, isSelected = isSelected, onClick = onClick)
            }
            CommonButtonColumn(
                header = "담당자 *",
                items = boardDataState.names(),
                isSelected = { boardDataState.isSelectedName(it) },
                onValueChange = { boardDataState.nameOnValueChange(it) },
            ) { name, isSelected, onClick ->
                CoachButton(name = name, isSelected = isSelected, onClick = onClick)
            }
            HorizontalDivider()
            FooterRow(
                onCancel = onDismissRequest,
                onCreate = {
                    val boardData = BoardData(
                        title = boardDataState.titleInputValue,
                        description = boardDataState.descriptionInputValue,
                        tags = boardDataState.changeTagsValue(),
                        status = boardDataState.statusValue,
                        nickname = boardDataState.nameValue,
                    )
                    onTaskCreate(boardData)
                },
                isCreateError = isCreateError,
            )
        }
    }
}

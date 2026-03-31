package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.Tag

class BoardDataState(
    val id: String = "",
    title: String = "",
    description: String = "",
    tags: String = "",
    status: Status = Status.TODO,
    name: String = "다이노",
) {
    var titleInputValue by mutableStateOf(title)
    var descriptionInputValue by mutableStateOf(description)
    var tagsInputValue by mutableStateOf(tags)
    var statusValue by mutableStateOf(status)
    var nameValue by mutableStateOf(name)

    var isTitleError by mutableStateOf(false)
    var isTagsError by mutableStateOf(false)

    fun titleOnValueChange(value: String) {
        titleInputValue = value
        isTitleError = BoardData.isTitleError(titleInputValue)
    }

    fun descriptionOnValueChange(value: String) {
        descriptionInputValue = value
    }

    fun tagsOnValueChange(value: String) {
        tagsInputValue = value
        val tags = if (tagsInputValue.isNotBlank()) tagsInputValue.split(",") else emptyList()
        isTagsError = tags.any { Tag.isTagError(it) } || BoardData.isTagsError(tags.map { Tag(it) })
    }

    fun statusOnValueChange(status: Status) {
        statusValue = status
    }

    fun isSelectedStatus(status: Status): Boolean {
        return statusValue == status
    }

    fun nameOnValueChange(name: String) {
        nameValue = name
    }

    fun isSelectedName(name: String): Boolean {
        return nameValue == name
    }

    fun changeTagsValue(): List<Tag> = if (tagsInputValue.isNotBlank()) tagsInputValue.split(",").map { Tag(it) } else emptyList()
}

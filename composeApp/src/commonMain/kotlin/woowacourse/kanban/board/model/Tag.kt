package woowacourse.kanban.board.model

data class Tag(val text: String) {
    init {
        require(!isTagError(text)) { "[ERROR] 태그에서 오류가 발생했습니다." }
    }

    companion object {
        const val MAX_TAG_LENGTH = 5
        fun isTagError(tag: String): Boolean = tag.length > MAX_TAG_LENGTH || tag.isBlank() || tag.count { it.toString().isBlank() } > 0
    }
}

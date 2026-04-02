package woowacourse.kanban.board.model

enum class DialogStatus {
    CREATE, EDIT;

    companion object {
        fun dialogTitle(dialogStatus: DialogStatus): String {
            return when(dialogStatus) {
                CREATE -> "새 태스크 생성"
                EDIT -> "기존 태스크 수정"
            }
        }
    }
}

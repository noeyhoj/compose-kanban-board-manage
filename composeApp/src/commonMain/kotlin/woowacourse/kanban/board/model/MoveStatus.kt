package woowacourse.kanban.board.model

enum class MoveStatus {
    SUCCESS,
    IN_PROGRESS,
    FAILED,
    ;

    companion object {
        fun getMoveStatus(task: BoardData, targetStatus: Status): MoveStatus {
            return when (task.status) {
                Status.TODO if ((targetStatus == Status.REVIEW || targetStatus == Status.DONE) || task.nickname == "") -> {
                    IN_PROGRESS
                }
                Status.IN_PROGRESS if (targetStatus == Status.DONE) -> {
                    FAILED
                }
                Status.REVIEW if (targetStatus == Status.TODO) -> {
                    FAILED
                }
                Status.DONE if (targetStatus == Status.IN_PROGRESS || targetStatus == Status.REVIEW) -> {
                    FAILED
                }
                else -> {
                    SUCCESS
                }
            }
        }
    }
}

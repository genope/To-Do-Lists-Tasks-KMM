package todolist.kmm.data_remote.model.mapper

import todolist.kmm.data_remote.model.Item
import note.data.local.Task
import todolist.kmm.domain.models.Complexity
import todolist.kmm.domain.models.Status
import todolist.kmm.domain.models.map.Mapper


class ApiTaskMapper : Mapper<Item, Task>() {
    override fun map(model: Item): Task = model.run {
        Task(
            id.toLong(), name_task, description, assigned_from, assigned_to,
            when (status) {
                0 -> Status.PENDING
                1 -> Status.DONE
                else -> {
                    Status.UNKNOWN
                }
            },
            when (complexity) {
                1 -> Complexity.EASY
                2 -> Complexity.MEDIUM
                3 -> Complexity.HARD
                else -> {
                    Complexity.UNKNOWN
                }
            },
        )
    }

    override fun inverseMap(model: Task): Item {
        TODO("Not yet implemented")
    }

    /*override fun inverseMap(model: Task): Item = model.run {
        Item(
            name_task, description, assigned_from, assigned_to,
            when (complexity) {
                Complexity.EASY -> 1
                Complexity.MEDIUM -> 2
                Complexity.HARD -> 3
                else -> {
                    Complexity.UNKNOWN
                }
            },
            when (status) {
                Status.PENDING -> 0
                Status.DONE -> 1
                else -> {
                    Status.UNKNOWN
                }
            },id
        )
    }*/
}


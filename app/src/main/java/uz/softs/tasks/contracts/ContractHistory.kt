package uz.softs.tasks.contracts

import uz.softs.tasks.data.local.room.entity.TaskData

interface ContractHistory {
    interface Model {
        fun getAll(): List<List<TaskData>>
    }

    interface View {
        fun loadData(list: List<List<TaskData>>)
        fun openItemDialog(data: TaskData)
        fun finishWindow()
    }

    interface Presenter {
        fun clickItem(data: TaskData)
        fun buttonClose()
    }
}
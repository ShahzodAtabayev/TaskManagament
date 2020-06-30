package uz.softs.tasks.contracts

import uz.softs.tasks.data.local.room.entity.TaskData


interface ContractSeeAllTask {
    interface Model {
        fun getAllTask(): List<List<TaskData>>
        fun update(taskData: TaskData)
    }

    interface View {
        fun loadDataToView(list: List<List<TaskData>>)
        fun openItemDialog(data: TaskData)
    }

    interface Presenter {
        fun clickItem(data: TaskData)
        fun buttonDone(taskData: TaskData)
        fun buttonCanceled(taskData: TaskData)
    }
}
package uz.softs.tasks.contracts

import uz.softs.tasks.data.local.room.entity.TaskData

interface ContractEditTasks {
    interface Model {
        fun getAll(): List<TaskData>
        fun update(data: TaskData): Int
        fun remove(data: TaskData): Int
    }

    interface View {
        fun loadData(list: List<TaskData>)
        fun finishWindow()
        fun openItemDialog(data: TaskData)
        fun openEditDialog(data: TaskData)
        fun cancelDialog()
    }

    interface Presenter {
        fun clickItem(data: TaskData)
        fun buttonRemove(data: TaskData)
        fun buttonCanceled(data: TaskData)
        fun buttonEdit(data: TaskData)
        fun buttonEditPopupMenu(data: TaskData)
        fun buttonClose()
    }
}
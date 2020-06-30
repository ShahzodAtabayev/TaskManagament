package uz.softs.tasks.contracts

import uz.softs.tasks.data.local.room.entity.TaskData


interface ContractTaskBasket {
    interface Model {
        fun getAll(): List<TaskData>
        fun update(data: TaskData): Int
        fun delete(data: TaskData): Int
    }

    interface View {
        fun loadData(list: List<TaskData>)
        fun openItemDialog(data: TaskData)
        fun finishWindow()

    }

    interface Presenter {
        fun clickItem(data: TaskData)
        fun buttonRemove(data: TaskData)
        fun buttonRestore(data: TaskData)
        fun buttonClose()
    }
}
package uz.softs.tasks.contracts

import android.content.Context
import uz.softs.tasks.data.local.room.entity.TaskData

interface ContractAddTask {
    interface Model {
        fun insert(data: TaskData)
        fun getAll(): List<TaskData>

    }

    interface View {
        fun finishAddTask()
        fun makeToast(message: String)
        fun getName(): String
        fun getInfo(): String
        fun getDate(): String
        fun getTime(): String
        fun getDegree(): String
        fun openDateDialog()
        fun openTimeDialog()
        fun changeErrorBackground(f: Int)
        fun changeEditTextBackground()
        fun getContext():Context
    }

    interface Presenter {
        fun addTask()
        fun clickDateDialog()
        fun clickTimeDialog()
        fun buttonClose()
    }
}
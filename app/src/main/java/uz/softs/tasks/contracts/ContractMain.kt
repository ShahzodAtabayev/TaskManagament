package uz.softs.tasks.contracts
import uz.softs.tasks.data.local.room.entity.TaskData


interface ContractMain {
    interface Model {
        fun getAllTask(): List<TaskData>
        fun getAllTask1(): List<TaskData>
        fun update(taskData: TaskData)
        fun getImageProfile(): String
        fun getFullNameProfile(): String
        fun isFirst(): Boolean
        fun setIsFirst(b: Boolean)
    }

    interface View {
        fun loadDataToView(list: List<TaskData>, image: String, fullName: String, isFirst: Boolean)
        fun openNavigation()
        fun addTask()
        fun seeAllTask()
        fun editTask()
        fun history()
        fun taskBasket()
        fun termOfUse()
        fun instructionToUse()
        fun settings()
        fun openItemDialog(data: TaskData)
        fun setRefreshing(boolean: Boolean)
        fun share()
    }

    interface Presenter {
        fun initData()
        fun clickItem(data: TaskData)
        fun buttonNavigationView()
        fun openAddTask()
        fun openSeeAllTask()
        fun openEditTask()
        fun openHistory()
        fun openTaskBasket()
        fun openTermOfUse()
        fun openInstructionToUse()
        fun openSettings()
        fun share()
    }
}
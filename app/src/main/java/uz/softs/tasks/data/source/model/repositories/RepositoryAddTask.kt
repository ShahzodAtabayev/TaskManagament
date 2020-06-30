package uz.softs.tasks.data.source.model.repositories

import uz.softs.tasks.apps.App
import uz.softs.tasks.contracts.ContractAddTask
import uz.softs.tasks.data.local.room.AppDatabase
import uz.softs.tasks.data.local.room.entity.TaskData

class RepositoryAddTask : ContractAddTask.Model{
    private val taskDao=AppDatabase.getDatabase(App.instance).taskDao()
    override fun insert(data: TaskData) {
        taskDao.insert(data)
    }

    override fun getAll(): List<TaskData> =taskDao.getActiveTasks(false)
}
package uz.softs.tasks.data.source.model.repositories

import uz.softs.tasks.apps.App
import uz.softs.tasks.contracts.ContractMain
import uz.softs.tasks.data.local.room.AppDatabase
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.local.storage.LocalStorage

class RepositoryMain : ContractMain.Model {
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()
    private val localStorage = LocalStorage.instance
    override fun getAllTask(): List<TaskData> = taskDao.getActiveTasks(false)
    override fun getAllTask1(): List<TaskData> = taskDao.getAll()

    override fun update(taskData: TaskData) {
        taskDao.update(taskData)
    }

    override fun getImageProfile(): String = localStorage.profileImage
    override fun getFullNameProfile(): String =localStorage.profileFullName
}
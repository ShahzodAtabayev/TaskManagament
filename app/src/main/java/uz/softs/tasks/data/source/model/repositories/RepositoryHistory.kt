package uz.softs.tasks.data.source.model.repositories

import uz.softs.tasks.apps.App
import uz.softs.tasks.contracts.ContractHistory
import uz.softs.tasks.data.local.room.AppDatabase
import uz.softs.tasks.data.local.room.entity.TaskData

class RepositoryHistory : ContractHistory.Model {
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()
    override fun getAll(): List<List<TaskData>> {
        val data = ArrayList<List<TaskData>>()
        data.add(taskDao.getDoneAll(true))
        data.add(taskDao.getCanceledAll(true))
        data.add(taskDao.getOutDateAll(true))
        return data
    }
}
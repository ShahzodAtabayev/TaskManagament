package uz.softs.tasks.data.source.model.repositories

import uz.softs.tasks.apps.App
import uz.softs.tasks.contracts.ContractSeeAllTask
import uz.softs.tasks.data.local.room.AppDatabase
import uz.softs.tasks.data.local.room.entity.TaskData

class RepositorySeeAllTask : ContractSeeAllTask.Model {
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()
    override fun getAllTask(): List<List<TaskData>> {
        val data = ArrayList<List<TaskData>>()
        data.add(taskDao.getDoneAll(true))
        data.add(taskDao.getActiveTasks(false))
        data.add(taskDao.getOutDateAll(true))
        data.add(taskDao.getCanceledAll(true))
        return data
    }
    override fun update(taskData: TaskData) {
        taskDao.update(taskData)
    }

}
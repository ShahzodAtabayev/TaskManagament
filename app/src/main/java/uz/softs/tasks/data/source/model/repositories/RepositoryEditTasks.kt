package uz.softs.tasks.data.source.model.repositories

import uz.softs.tasks.apps.App
import uz.softs.tasks.contracts.ContractEditTasks
import uz.softs.tasks.data.local.room.AppDatabase
import uz.softs.tasks.data.local.room.entity.TaskData

class RepositoryEditTasks : ContractEditTasks.Model {
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()
    override fun getAll(): List<TaskData> {
        val ls=ArrayList<TaskData>()
        ls.addAll(taskDao.getActiveTasks(false))
        ls.addAll(taskDao.getDoneAll(true))
        ls.addAll(taskDao.getOutDateAll(true))
        ls.addAll(taskDao.getCanceledAll(true))
        return ls
    }

    override fun update(data: TaskData): Int = taskDao.update(data)

    override fun remove(data: TaskData): Int = taskDao.delete(data)

}
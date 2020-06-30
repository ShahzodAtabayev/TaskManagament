package uz.softs.tasks.data.source.model.repositories

import uz.softs.tasks.apps.App
import uz.softs.tasks.contracts.ContractTaskBasket
import uz.softs.tasks.data.local.room.AppDatabase
import uz.softs.tasks.data.local.room.entity.TaskData

class RepositoryTaskBasket : ContractTaskBasket.Model {
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()

    override fun getAll(): List<TaskData> = taskDao.getDeleteAll(true)

    override fun update(data: TaskData): Int = taskDao.update(data)

    override fun delete(data: TaskData): Int =taskDao.delete(data)
}
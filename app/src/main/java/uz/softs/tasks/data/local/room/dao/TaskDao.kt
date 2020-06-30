package uz.softs.tasks.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import uz.softs.tasks.data.local.room.entity.TaskData

@Dao
interface TaskDao : BaseDao<TaskData> {
    @Query("SELECT*FROM TaskData")
    fun getAll(): List<TaskData>

    @Query("SELECT*FROM TaskData WHERE isCanceled=:boolean AND isDelete==0 ")
    fun getCanceledAll(boolean: Boolean): List<TaskData>

    @Query("SELECT*FROM TaskData WHERE isDone=:boolean AND isDelete==0 ")
    fun getDoneAll(boolean: Boolean): List<TaskData>

    @Query("SELECT*FROM TaskData WHERE isOutDated=:boolean AND isDelete==0 ")
    fun getOutDateAll(boolean: Boolean): List<TaskData>

    @Query("SELECT*FROM TaskData WHERE isOutDated=:boolean AND isDelete=:boolean AND isDone=:boolean  AND isCanceled=:boolean ")
    fun getActiveTasks(boolean: Boolean): List<TaskData>

    @Query("SELECT*FROM TaskData WHERE isDelete=:boolean")
    fun getDeleteAll(boolean: Boolean): List<TaskData>

    @Query("SELECT*FROM TaskData WHERE id=:id")
    fun getDataById(id: Long): TaskData

}
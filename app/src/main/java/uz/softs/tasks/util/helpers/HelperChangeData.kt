package uz.softs.tasks.util.helpers

import android.annotation.SuppressLint
import uz.softs.tasks.apps.App
import uz.softs.tasks.data.local.room.AppDatabase
import uz.softs.tasks.data.local.room.entity.TaskData
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class HelperChangeData {

    companion object {
        private val executor = Executors.newSingleThreadExecutor()
        private val date = Date()

        @SuppressLint("SimpleDateFormat")
        val currentYear = SimpleDateFormat("yyyy").format(date).toInt()

        @SuppressLint("SimpleDateFormat")
        val currentMonth = SimpleDateFormat("M").format(date).toInt()

        @SuppressLint("SimpleDateFormat")
        val currentDay = SimpleDateFormat("d").format(date).toInt()


        @SuppressLint("SimpleDateFormat")
        fun changeData(ls: List<TaskData>): List<TaskData> {
            val data = ArrayList<TaskData>(ls)
            val taskDao = AppDatabase.getDatabase(App.instance).taskDao()
            val date = Date()
            val currentYear = SimpleDateFormat("yyyy").format(date).toInt()
            val currentMonth = SimpleDateFormat("M").format(date).toInt()
            val currentDay = SimpleDateFormat("d").format(date).toInt()
            val currentHour = SimpleDateFormat("H").format(date).toInt()
            val currentMin = SimpleDateFormat("m").format(date).toInt()
            for (i in data.indices) {
                if (data[i].isOutDated || data[i].isDelete || data[i].isCanceled || data[i].isDone) continue
                val min = data[i].time.substring(3, 5).toInt()
                val hour = data[i].time.substring(0, 2).toInt()
                val day = data[i].date.substring(0, 2).toInt()
                val month = data[i].date.substring(3, 5).toInt()
                val year = data[i].date.substring(6, 10).toInt()
                if (year - currentYear < 0) {
                    runOnWorkerThread {
                        data[i].isOutDated = true
                        taskDao.update(data[i])
                        data.removeAt(i)
                    }
                    continue
                }
                if (year - currentYear == 0 && month - currentMonth < 0) {
                    runOnWorkerThread {
                        data[i].isOutDated = true
                        taskDao.update(data[i])
                        data.removeAt(i)
                    }
                    continue
                }
                if (year - currentYear == 0 && month - currentMonth == 0 && day - currentDay < 0) {
                    runOnWorkerThread {
                        data[i].isOutDated = true
                        taskDao.update(data[i])
                        data.removeAt(i)
                    }
                    continue
                }
                if (year - currentYear == 0 && month - currentMonth == 0 && day - currentDay == 0 && hour - currentHour < 0) {
                    runOnWorkerThread {
                        data[i].isOutDated = true
                        taskDao.update(data[i])
                        data.removeAt(i)
                    }
                    continue
                }
                if (year - currentYear == 0 && month - currentMonth == 0 && day - currentDay == 0 && hour - currentHour == 0 && min - currentMin < 0) {
                    runOnWorkerThread {
                        data[i].isOutDated = true
                        taskDao.update(data[i])
                        data.removeAt(i)
                    }
                    continue
                }
            }
            return data
        }

        fun sorted(ls: List<TaskData>): ArrayList<TaskData> {
            val data = ArrayList<TaskData>(ls)
            for (i in data.indices) {
                for (j in data.indices) {
                    val dayI = data[i].date.substring(0, 2).toInt()
                    val dayJ = data[j].date.substring(0, 2).toInt()
                    val monthI = data[i].date.substring(3, 5).toInt()
                    val monthJ = data[j].date.substring(3, 5).toInt()
                    val yearI = data[i].date.substring(6, 10).toInt()
                    val yearJ = data[j].date.substring(6, 10).toInt()
                    val hourI = data[i].time.substring(0, 2).toInt()
                    val hourJ = data[j].time.substring(0, 2).toInt()
                    val minI = data[i].time.substring(3, 5).toInt()
                    val minJ = data[j].time.substring(3, 5).toInt()
                    if (yearI < yearJ) {
                        val temp = data[j]
                        data[j] = data[i]
                        data[i] = temp
                        continue
                    }
                    if (yearI == yearJ && monthI < monthJ) {
                        val temp = data[j]
                        data[j] = data[i]
                        data[i] = temp
                        continue
                    }
                    if (yearI == yearJ && monthI == monthJ && dayI < dayJ) {
                        val temp = data[j]
                        data[j] = data[i]
                        data[i] = temp
                        continue
                    }
                    if (yearI == yearJ && monthI == monthJ && dayI == dayJ && hourI < hourJ) {
                        val temp = data[j]
                        data[j] = data[i]
                        data[i] = temp
                        continue
                    }
                    if (yearI == yearJ && monthI == monthJ && dayI == dayJ && hourI == hourJ && minI < minJ) {
                        val temp = data[j]
                        data[j] = data[i]
                        data[i] = temp
                        continue
                    }
                }
            }
            return data
        }

        private fun runOnWorkerThread(f: () -> Unit) {
            executor.execute { f() }
        }
    }
}
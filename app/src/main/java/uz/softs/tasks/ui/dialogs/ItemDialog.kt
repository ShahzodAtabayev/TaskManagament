package uz.softs.tasks.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import uz.softs.tasks.R
import uz.softs.tasks.data.local.room.entity.TaskData
import kotlinx.android.synthetic.main.item_dialog.view.*
import kotlinx.android.synthetic.main.item_dialog.view.taskDialogTimer
import uz.softs.tasks.util.helpers.MyCount
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class ItemDialog(context: Context) : AlertDialog(context) {

    private val view: View = LayoutInflater.from(context).inflate(R.layout.item_dialog, null, false)
    private var diff: Long = 0
    var oldLong: Long = 0
    var newLong: Long = 0

    init {
        setView(view)
        view.buttonCloseDialog.setOnClickListener { this.cancel() }
    }

    @SuppressLint("SimpleDateFormat")
    fun loadData(data: TaskData) {
        view.apply {
            taskDialogName.text = data.name
            taskDialogInfo.text = data.info
            taskDialogDate.text = data.date
            taskDialogTime.text = data.time
            taskDialogDegree.text = data.degree.toString()
        }
        val formatter = SimpleDateFormat("dd.MM.yyyy,HH:mm")
        val taskDate = data.date + "," + data.time
        val currentDate = SimpleDateFormat("dd.MM.yyyy,HH:mm").format(Date())
        Log.d("TTTT", "taskDate=$taskDate")
        Log.d("TTTT", "currentDate=$currentDate")
        val taskDate1: Date
        val currentDate1: Date
        try {
            taskDate1 = formatter.parse(taskDate)
            currentDate1 = formatter.parse(currentDate)
            oldLong = taskDate1.time
            newLong = currentDate1.time
            diff = oldLong - newLong
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (diff > 0) {
            val counter = MyCount(diff, 1000, view.taskDialogTimer, true, context)
            counter.start()
        } else {
            val counter = MyCount(abs(diff), 1000, view.taskDialogTimer, false, context)
            counter.start()
        }
    }
}

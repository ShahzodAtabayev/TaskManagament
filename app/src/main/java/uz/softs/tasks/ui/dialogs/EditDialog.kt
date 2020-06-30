package uz.softs.tasks.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.FragmentManager
import uz.softs.tasks.R
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.source.model.DegreeData
import uz.softs.tasks.ui.adapters.DegreeSpinnerAdapter
import uz.softs.tasks.util.helpers.ItemClick
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.item_edit_task_dialog.*
import kotlinx.android.synthetic.main.item_edit_task_dialog.view.*
import kotlinx.android.synthetic.main.item_edit_task_dialog.view.editTaskName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditDialog(context: Context, fragmentManager: FragmentManager) : AlertDialog(context) {
    private var listener: ItemClick<TaskData>? = null
    private val spinnerArray = ArrayList<DegreeData>()
    private lateinit var degree: DegreeData
    private lateinit var data: TaskData

    @SuppressLint("InflateParams")
    private val view: View =
        LayoutInflater.from(context).inflate(R.layout.item_edit_task_dialog, null, false)

    init {
        setView(view)
        spinnerArray.add(DegreeData(R.drawable.ic_one, context.resources.getString(R.string.very_important)))
        spinnerArray.add(DegreeData(R.drawable.ic_two, context.resources.getString(R.string.important)))
        spinnerArray.add(DegreeData(R.drawable.ic_three, context.resources.getString(R.string.more_important)))
        spinnerArray.add(DegreeData(R.drawable.ic_four, context.resources.getString(R.string.not_so_important)))
        val spinnerAdapter = DegreeSpinnerAdapter(context, spinnerArray)
        view.editTaskDegree.adapter = spinnerAdapter
        view.editTaskDegree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                degree = spinnerArray[3]
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                degree = spinnerArray[position]
            }
        }
        view.editTaskDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val d = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                editTaskDate.setText(SimpleDateFormat("dd.MM.yyyy").format(cal.time))
            }
            d.show(fragmentManager, "")
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                editTaskTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        view.editTaskTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                editTaskTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        view.buttonEditTask.setOnClickListener {
            listener?.invoke(edit())
            cancel()
        }
        view.buttonBackEditDialog.setOnClickListener { cancel() }
    }

    private fun edit(): TaskData = TaskData(
        id = data.id,
        name = editTaskName.text.toString(),
        info = editTaskInfo.text.toString(),
        date = editTaskDate.text.toString(),
        time = editTaskTime.text.toString(),
        isCanceled = data.isCanceled,
        isDelete = data.isDelete,
        isDone = data.isDone,
        isOutDated = data.isOutDated,
        degree = when (degree.icon) {
            R.drawable.ic_one -> "1".toByte()
            R.drawable.ic_two -> "2".toByte()
            R.drawable.ic_three -> "3".toByte()
            R.drawable.ic_four -> "4".toByte()
            else -> "4".toByte()
        }
    )

    fun setOnEditListener(f: ItemClick<TaskData>) {
        listener = f
    }

    fun loadData(data: TaskData) {
        view.apply {
            editTaskName.setText(data.name)
            editTaskInfo.setText(data.info)
            editTaskDate.setText(data.date)
            editTaskTime.setText(data.time)
        }
        this.data = data
    }
}

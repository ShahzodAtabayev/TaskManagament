package uz.softs.tasks.ui.screens

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import uz.softs.tasks.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_add_task.*
import uz.softs.tasks.contracts.ContractAddTask
import uz.softs.tasks.data.source.model.DegreeData
import uz.softs.tasks.data.source.model.repositories.RepositoryAddTask
import uz.softs.tasks.presenters.PresenterAddTask
import uz.softs.tasks.ui.adapters.DegreeSpinnerAdapter
import uz.softs.tasks.util.extension.changeNavigationBarColor
import uz.softs.tasks.util.extension.changeStatusBarColor
import uz.softs.tasks.util.extension.toDarkenColor
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddTaskActivity : LocaleAwareCompatActivity(), ContractAddTask.View {
    private lateinit var presenter: ContractAddTask.Presenter
    private val spinnerArray = ArrayList<DegreeData>()
    private lateinit var degree: DegreeData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        loadView()
        presenter = PresenterAddTask(RepositoryAddTask(), this)
        //o'zgardi
        // yana o'zgardi
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun loadView() {
        buttonAddTask.setOnClickListener { presenter.addTask() }
        inputTaskDate.setOnClickListener { presenter.clickDateDialog() }
        inputTaskTime.setOnClickListener { presenter.clickTimeDialog() }
        buttonBackAddTask.setOnClickListener { presenter.buttonClose() }
        spinnerArray.add(DegreeData(R.drawable.ic_one, resources.getString(R.string.very_important)))
        spinnerArray.add(DegreeData(R.drawable.ic_two, resources.getString(R.string.important)))
        spinnerArray.add(DegreeData(R.drawable.ic_three, resources.getString(R.string.more_important)))
        spinnerArray.add(DegreeData(R.drawable.ic_four, resources.getString(R.string.not_so_important)))
        val spinnerAdapter = DegreeSpinnerAdapter(this, spinnerArray)
        inputTaskDegree.adapter = spinnerAdapter
        inputTaskDegree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        changeStatusBarColor(Color.parseColor("#53FDED").toDarkenColor())
        changeNavigationBarColor(Color.parseColor("#53FDED").toDarkenColor())
    }

    override fun finishAddTask() {
        finish()
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun getName(): String = inputTaskName.text.toString()
    override fun getInfo(): String = inputTaskInfo.text.toString()
    override fun getDate(): String = inputTaskDate.text.toString()
    override fun getTime(): String = inputTaskTime.text.toString()
    override fun getDegree(): String = when (degree.icon) {
        R.drawable.ic_one -> "1"
        R.drawable.ic_two -> "2"
        R.drawable.ic_three -> "3"
        R.drawable.ic_four -> "4"
        else -> "4"
    }

    @SuppressLint("SimpleDateFormat")
    override fun openDateDialog() {
        val cal = Calendar.getInstance()
        val d = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            inputTaskDate.setText(SimpleDateFormat("dd.MM.yyyy").format(cal.time))
        }
        d.show(supportFragmentManager, "")
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            inputTaskTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }
        TimePickerDialog(
            this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
        ).show()
    }

    override fun changeErrorBackground(f: Int) {
        when(f){
            1->inputTaskName.setBackgroundResource(R.drawable.bg_edit_text_error)
            2->inputTaskInfo.setBackgroundResource(R.drawable.bg_edit_text_error)
            3->inputTaskDate.setBackgroundResource(R.drawable.bg_edit_text_error)
            4->inputTaskTime.setBackgroundResource(R.drawable.bg_edit_text_error)
        }
    }

    override fun changeEditTextBackground() {
        inputTaskName.setBackgroundResource(R.drawable.bg_edit_text)
        inputTaskInfo.setBackgroundResource(R.drawable.bg_edit_text)
        inputTaskDate.setBackgroundResource(R.drawable.bg_edit_text)
        inputTaskTime.setBackgroundResource(R.drawable.bg_edit_text)
    }

    override fun getContext(): Context =this

    @SuppressLint("SimpleDateFormat")
    override fun openTimeDialog() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            inputTaskTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }
        TimePickerDialog(
            this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
        ).show()
    }
}

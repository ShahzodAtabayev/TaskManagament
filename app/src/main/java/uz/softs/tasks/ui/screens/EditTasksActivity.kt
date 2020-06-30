package uz.softs.tasks.ui.screens

import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import uz.softs.tasks.R
import uz.softs.tasks.contracts.ContractEditTasks
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.source.model.repositories.RepositoryEditTasks
import uz.softs.tasks.presenters.PresenterEditTask
import uz.softs.tasks.ui.adapters.EditTasksAdapter
import uz.softs.tasks.ui.dialogs.EditDialog
import uz.softs.tasks.ui.dialogs.ItemDialog
import uz.softs.tasks.util.extension.changeNavigationBarColor
import uz.softs.tasks.util.extension.changeStatusBarColor
import uz.softs.tasks.util.extension.toDarkenColor
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_edit_tasks.*

class EditTasksActivity : LocaleAwareCompatActivity(), ContractEditTasks.View {
    private lateinit var presenter: ContractEditTasks.Presenter
    private val adapter = EditTasksAdapter()
    private lateinit var dialog: EditDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tasks)
        loadView()
        presenter = PresenterEditTask(RepositoryEditTasks(), this)
    }

    private fun loadView() {
        listEditTasks.adapter = adapter
        listEditTasks.layoutManager = LinearLayoutManager(this)
        buttonCloseEditTask.setOnClickListener { presenter.buttonClose() }
        adapter.setOnItemClickListener { presenter.clickItem(it) }
        adapter.setOnItemCanceledListener { presenter.buttonCanceled(it) }
        adapter.setOnItemDeleteListener { presenter.buttonRemove(it) }
        adapter.setOnItemEditListener { presenter.buttonEditPopupMenu(it) }
        dialog = EditDialog(this, supportFragmentManager)
        dialog.setOnEditListener { presenter.buttonEdit(it) }
        changeStatusBarColor(Color.parseColor("#FF92B6").toDarkenColor())
        changeNavigationBarColor(Color.parseColor("#FF92B6").toDarkenColor())
    }

    override fun loadData(list: List<TaskData>) {
        adapter.submitList(list)
    }

    override fun finishWindow() {
        finish()
    }

    override fun openItemDialog(data: TaskData) {
        val dialog = ItemDialog(this)
        dialog.loadData(data)
        dialog.show()
    }

    override fun openEditDialog(data: TaskData) {
        dialog.loadData(data)
        dialog.show()
    }

    override fun cancelDialog() {
        dialog.cancel()
    }
}

package uz.softs.tasks.ui.screens

import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import uz.softs.tasks.R
import uz.softs.tasks.contracts.ContractTaskBasket
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.source.model.repositories.RepositoryTaskBasket
import uz.softs.tasks.presenters.PresenterTaskBasket
import uz.softs.tasks.ui.adapters.TaskBasketAdapter
import uz.softs.tasks.ui.dialogs.ItemDialog
import uz.softs.tasks.util.extension.changeNavigationBarColor
import uz.softs.tasks.util.extension.changeStatusBarColor
import uz.softs.tasks.util.extension.toDarkenColor
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_task_basket.*

class TaskBasketActivity : LocaleAwareCompatActivity(), ContractTaskBasket.View {
    private lateinit var presenter: ContractTaskBasket.Presenter
    private val adapter = TaskBasketAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_basket)
        loadView()
        presenter = PresenterTaskBasket(RepositoryTaskBasket(), this)

    }

    private fun loadView() {
        listTaskBasket.adapter = adapter
        listTaskBasket.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.setOnItemClickListener { presenter.clickItem(it) }
        adapter.setOnItemDeleteListener { presenter.buttonRemove(it) }
        adapter.setOnItemRestoreListener { presenter.buttonRestore(it) }
//        listTaskBasket.animation = AnimationUtils.loadAnimation(this, R.anim.fade_scale_animation)
        buttonCloseTaskBasket.setOnClickListener { presenter.buttonClose() }
        changeStatusBarColor(Color.parseColor("#8E5AAE").toDarkenColor())
        changeNavigationBarColor(Color.parseColor("#8E5AAE").toDarkenColor())
    }

    override fun loadData(list: List<TaskData>) {
        adapter.submitList(list)
    }

    override fun openItemDialog(data: TaskData) {
        val dialog = ItemDialog(this)
        dialog.loadData(data)
        dialog.show()
    }

    override fun finishWindow() {
        finish()
    }
}

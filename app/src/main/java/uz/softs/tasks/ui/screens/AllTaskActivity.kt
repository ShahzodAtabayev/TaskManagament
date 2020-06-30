package uz.softs.tasks.ui.screens


import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import uz.softs.tasks.R
import uz.softs.tasks.contracts.ContractSeeAllTask
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.source.model.ColorsArray.Companion.allTaskActivityColors
import uz.softs.tasks.data.source.model.ImagesArray.Companion.seeAllTaskActivityImages
import uz.softs.tasks.data.source.model.repositories.RepositorySeeAllTask
import uz.softs.tasks.presenters.PresenterSeeAllTask
import uz.softs.tasks.ui.adapters.AllTaskPagerAdapter
import uz.softs.tasks.ui.dialogs.ItemDialog
import uz.softs.tasks.util.extension.changeNavigationBarColor
import uz.softs.tasks.util.extension.changeStatusBarColor
import uz.softs.tasks.util.extension.toDarkenColor
import com.google.android.material.tabs.TabLayoutMediator
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_see_all_task.*


class AllTaskActivity : LocaleAwareCompatActivity(), ContractSeeAllTask.View {
    private lateinit var adapter: AllTaskPagerAdapter
    private lateinit var presenter: ContractSeeAllTask.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_task)
        loadView()
        presenter = PresenterSeeAllTask(RepositorySeeAllTask(), this)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.done)
                1 -> tab.text = resources.getString(R.string.new_)
                2 -> tab.text = resources.getString(R.string.outdated)
                3 -> tab.text = resources.getString(R.string.canceled)
            }
        }.attach()
    }

    private fun loadView() {
        adapter = AllTaskPagerAdapter(this)
        pager.adapter = adapter
        pager.registerOnPageChangeCallback(pageChangeCallback)
        buttonCloseSeeAllTask.setOnClickListener { finish() }
        adapter.setOnItemCanceledListener { presenter.buttonCanceled(it) }
        adapter.setOnItemDoneListener { presenter.buttonDone(it) }
        adapter.setOnItemClickListener { presenter.clickItem(it) }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            changeStatusBarColor(allTaskActivityColors[position].toDarkenColor())
            changeNavigationBarColor(allTaskActivityColors[position].toDarkenColor())
            backgroundSeeAllTask.setBackgroundResource(seeAllTaskActivityImages[position])
            tabLayout.setBackgroundColor(allTaskActivityColors[position])
        }
    }

    override fun loadDataToView(list: List<List<TaskData>>) {
        adapter.submitData(list)
    }

    override fun openItemDialog(data: TaskData) {
        val dialog = ItemDialog(this)
        dialog.loadData(data)
        dialog.show()
    }
}

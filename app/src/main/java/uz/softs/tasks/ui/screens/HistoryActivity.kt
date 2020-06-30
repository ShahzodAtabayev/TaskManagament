package uz.softs.tasks.ui.screens

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import uz.softs.tasks.R
import uz.softs.tasks.contracts.ContractHistory
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.source.model.ColorsArray.Companion.historyActivityColors
import uz.softs.tasks.data.source.model.ImagesArray.Companion.historyActivityImages
import uz.softs.tasks.data.source.model.repositories.RepositoryHistory
import uz.softs.tasks.presenters.PresenterHistory
import uz.softs.tasks.ui.adapters.HistoryPagerAdapter
import uz.softs.tasks.ui.dialogs.ItemDialog
import uz.softs.tasks.util.extension.changeNavigationBarColor
import uz.softs.tasks.util.extension.changeStatusBarColor
import uz.softs.tasks.util.extension.toDarkenColor
import com.google.android.material.tabs.TabLayoutMediator
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : LocaleAwareCompatActivity(), ContractHistory.View {
    private lateinit var presenter: ContractHistory.Presenter
    private lateinit var adapter: HistoryPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        loadView()
        presenter = PresenterHistory(RepositoryHistory(), this)
        loadTabLayout()
    }

    private fun loadTabLayout() {
        TabLayoutMediator(tabLayoutHistory, pagerHistory) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.done)
                1 -> tab.text = resources.getString(R.string.canceled)
                2 -> tab.text = resources.getString(R.string.outdated)
            }
        }.attach()
    }

    private fun loadView() {
        adapter = HistoryPagerAdapter(this)
        pagerHistory.adapter = adapter
        pagerHistory.registerOnPageChangeCallback(pageChangeCallback)
        buttonCloseHistory.setOnClickListener { presenter.buttonClose() }
        adapter.setOnItemClickListener { presenter.clickItem(it) }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            changeStatusBarColor(historyActivityColors[position].toDarkenColor())
            changeNavigationBarColor(historyActivityColors[position].toDarkenColor())
            backgroundHistoryActivity.setBackgroundResource(historyActivityImages[position])
            tabLayoutHistory.setBackgroundColor(historyActivityColors[position])
        }
    }

    override fun loadData(list: List<List<TaskData>>) {
        adapter.submitData(list)
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

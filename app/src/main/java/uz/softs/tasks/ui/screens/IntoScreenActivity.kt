package uz.softs.tasks.ui.screens

import android.content.Intent
import android.os.Bundle
import uz.softs.tasks.R
import uz.softs.tasks.contracts.ContractIntoScreen
import uz.softs.tasks.data.source.model.IntoScreenData
import uz.softs.tasks.data.source.model.repositories.RepositoryIntoScreen
import uz.softs.tasks.presenters.PresenterIntoScreen
import uz.softs.tasks.ui.adapters.IntoScreenAdapter
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_into_screen.*

class IntoScreenActivity : LocaleAwareCompatActivity(), ContractIntoScreen.View {
    private lateinit var adapter: IntoScreenAdapter
    private lateinit var presenter: ContractIntoScreen.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_into_screen)
        loadViews()
        presenter = PresenterIntoScreen(RepositoryIntoScreen(), this)
    }

    private fun loadViews() {
        adapter = IntoScreenAdapter(this)
        pagerIntoScreen.adapter = adapter
        indicator.setViewPager2(pagerIntoScreen)
        buttonNext.setOnClickListener {
            presenter.buttonNext(pagerIntoScreen.currentItem)
        }
    }

    override fun loadData(list: List<IntoScreenData>) {
        adapter.submitList(list)
    }

    override fun setCurrentItem(item: Int) {
        pagerIntoScreen.setCurrentItem(item, true)
    }

    override fun openActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun finishWindow() {
        finish()
    }
}

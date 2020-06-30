package uz.softs.tasks.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_term_of_use.*
import uz.softs.tasks.R



class TermsOfUseActivity : LocaleAwareCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_of_use)
        loadView()
    }

    @SuppressLint("SimpleDateFormat")
    private fun loadView() {
        buttonCloseTermOfUse.setOnClickListener { finish() }
    }
}



package uz.softs.tasks.ui.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import uz.softs.tasks.R
import uz.softs.tasks.data.local.storage.LocalStorage
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity

class SplashActivity : LocaleAwareCompatActivity() {

    private val splashTimeOut: Long = 1400
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            if (LocalStorage.instance.isFirst) startActivity(Intent(this, IntoScreenActivity::class.java))
            else startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTimeOut)
    }
}

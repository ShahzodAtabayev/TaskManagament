package uz.softs.tasks.util.extension

import android.content.res.Resources
import java.util.*

fun Resources.changeLanguage(language: String) {
    configuration.setLocale(Locale(language))
    updateConfiguration(configuration, displayMetrics)
}
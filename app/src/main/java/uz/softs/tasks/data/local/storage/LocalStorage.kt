package uz.softs.tasks.data.local.storage

import android.content.Context
import com.securepreferences.SecurePreferences
import uz.softs.tasks.R

class LocalStorage private constructor(context: Context) {
    companion object {
        lateinit var instance: LocalStorage; private set

        fun init(context: Context) {
            instance = LocalStorage(context)
        }
    }

    private val pref = SecurePreferences(context, "tasks1.0.0", "tasks")
    var profileImage: String by StringPreference(pref, R.drawable.person.toString())
    var profileFullName: String by StringPreference(pref, "Shahzod Atabayev")
    var isFirst: Boolean by BooleanPreference(pref, true)
    var checked:Int by IntPreference(pref, R.id.rb4)
}
package uz.softs.tasks.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.widget.RadioButton
import uz.softs.tasks.R
import uz.softs.tasks.data.local.storage.LocalStorage
import kotlinx.android.synthetic.main.item_dialog_change_language.view.*

class ChangeLanguageDialog(context: Context, resources: Resources) : AlertDialog(context) {
    @SuppressLint("InflateParams")
    private val view = LayoutInflater.from(context).inflate(R.layout.item_dialog_change_language, null)
    private var oldCheck = LocalStorage.instance.checked
    var oldLanguage = "en"
    private var listener: ((String, Int) -> Unit)? = null
    fun confirm(f: (String, Int) -> Unit) {
        listener = f
    }

    fun checkRadio(id: Int) {
        view.buttonRadioGroup.check(id)
    }

    init {
        setView(view)
        view.apply {
            buttonRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rb1 -> oldLanguage = "uz"
                    R.id.rb2 -> oldLanguage = "fr"
                    R.id.rb3 -> oldLanguage = "ru"
                    R.id.rb4 -> oldLanguage = "en"
                }
                oldCheck = checkedId
            }
            view.buttonConfirm.setOnClickListener {
                listener?.invoke(oldLanguage, oldCheck)
                cancel()
            }
        }
    }
}
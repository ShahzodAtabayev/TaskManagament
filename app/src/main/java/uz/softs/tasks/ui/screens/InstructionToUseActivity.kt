package uz.softs.tasks.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_instruction_to_use.*
import uz.softs.tasks.R
import kotlin.math.roundToInt

class InstructionToUseActivity : LocaleAwareCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction_to_use)
        loadView()
    }

    @SuppressLint("SetTextI18n")
    private fun loadView() {
        buttonCloseInstructionToUse.setOnClickListener { finish() }
    }
}

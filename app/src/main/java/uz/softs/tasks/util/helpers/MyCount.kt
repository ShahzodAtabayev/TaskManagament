package uz.softs.tasks.util.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import uz.softs.tasks.R
import uz.softs.tasks.apps.App
import java.util.concurrent.TimeUnit


class MyCount(millisInFuture: Long, countDownInterval: Long, private val txt: TextView, private val isAbs: Boolean, private val context: Context) :
    CountDownTimer(millisInFuture, countDownInterval) {
    override fun onFinish() {
    }

    @SuppressLint("SetTextI18n")
    override fun onTick(millisUntilFinished: Long) {
        val hms = (TimeUnit.MILLISECONDS.toDays(millisUntilFinished).toString() + " " + context.resources.getString(R.string.day) + " "
                + ((TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished))).toString() + ":")
                + ((TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))).toString() + ":"
                + (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        ))))
        if (isAbs) txt.text = hms else {
            txt.text = "-$hms"
            cancel()
        }
    }
}
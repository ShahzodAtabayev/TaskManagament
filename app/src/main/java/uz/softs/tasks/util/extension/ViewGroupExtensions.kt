package uz.softs.tasks.util.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes id: Int): View = LayoutInflater.from(context).inflate(id,this,false)
package uz.softs.tasks.ui.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.softs.tasks.R
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.ItemClick
import uz.softs.tasks.util.extension.inflate
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.currentDay
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.currentMonth
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.currentYear
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.sorted
import kotlinx.android.synthetic.main.task_item.view.*
import kotlin.math.abs

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var listener: ItemClick<TaskData>? = null
    private val differ = AsyncListDiffer(this, ITEM_DIFF)

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<TaskData>() {
            override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData) =
                oldItem.isDone == newItem.isDone && oldItem.isOutDated == newItem.isOutDated &&
                        oldItem.isCanceled == newItem.isCanceled && oldItem.isDelete == newItem.isDelete && oldItem.name == newItem.name && oldItem.info ==
                        newItem.info && oldItem.date == newItem.date && oldItem.time == newItem.time && oldItem.degree == newItem.degree
        }
    }

    fun submitList(list: List<TaskData>) {
        differ.submitList(sorted(list))
    }

    fun setOnItemClickListener(f: ItemClick<TaskData>) {
        listener = f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.task_item))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.itemClick.setOnClickListener {
                listener?.invoke(differ.currentList[adapterPosition])
            }
        }

        @SuppressLint("ResourceAsColor")
        fun bind() {
            itemView.apply {
                this.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)
                val d = differ.currentList[adapterPosition]
                val day = d.date.substring(0, 2).toInt()
                val year = d.date.substring(6, 10).toInt()
                val month = d.date.substring(3, 5).toInt()
                if (year == currentYear && month == currentMonth && currentDay < 28) {
                    when (day - currentDay) {
                        0 -> {
                            setColorToViews(R.color.colorRed)
                        }
                        1 -> {
                            setColorToViews(R.color.colorRed)
                        }
                        in 2..3 -> {
                            setColorToViews(R.color.colorYellow)
                        }
                        else -> {
                            setColorToViews(R.color.colorGreen)
                        }
                    }
                } else if ((abs(day - currentDay) == 28 || abs(day - currentDay) == 29) && year == currentYear && currentDay >= 28) {
                    setColorToViews(R.color.colorYellow)
                }
                if ((abs(day - currentDay) == 30) && year == currentYear && currentDay >= 28) setColorToViews(R.color.colorRed)
                if (year - currentYear > 0) setColorToViews(R.color.colorGreen)
                textTaskName.text = d.name
                textTaskInfo.text = d.info
                textTaskDate.text = d.date
                textTaskTime.text = d.time
                textTaskDegree.text = d.degree.toString()
            }
        }

        private fun setColorToViews(@ColorRes color: Int) {
            itemView.apply {
                symbol.setBackgroundResource(color)
                val shape = textTaskDegree.background as GradientDrawable
                shape.setColor(ContextCompat.getColor(context, color))
                textTaskDegree.background = shape
            }
        }
    }
}
package uz.softs.tasks.ui.adapters

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
import kotlinx.android.synthetic.main.task_item.view.*


class HistoryRecycleAdapter : RecyclerView.Adapter<HistoryRecycleAdapter.ViewHolder>() {
    private var listener: ItemClick<TaskData>? = null
    private val differ = AsyncListDiffer(this, ITEM_DIFF)

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<TaskData>() {
            override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData) =
                oldItem.isDone == newItem.isDone && oldItem.isOutDated == newItem.isOutDated && oldItem.isCanceled == newItem.isCanceled
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =ViewHolder(parent.inflate(R.layout.task_item))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()


    fun setOnItemClickListener(f: ItemClick<TaskData>) {
        listener = f
    }

    fun submitList(list: List<TaskData>) {
        differ.submitList(list)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.itemClick.setOnClickListener { listener?.invoke(differ.currentList[adapterPosition]) }
        }

        fun bind() {
            itemView.apply {
                this.animation=AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation)
                val d = differ.currentList[adapterPosition]
                textTaskName.text = d.name
                textTaskInfo.text = d.info
                textTaskDate.text = d.date
                textTaskTime.text = d.time
                textTaskDegree.text = d.degree.toString()
                if (d.isDone && !d.isDelete) {
                    setColorToViews(R.color.colorGreen)
                    return
                }
                if (d.isCanceled && !d.isDelete) {
                    setColorToViews(R.color.colorCanceled)
                    return
                }
                if (d.isOutDated && !d.isDelete) {
                    setColorToViews(R.color.colorOutDated)
                    return
                }
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



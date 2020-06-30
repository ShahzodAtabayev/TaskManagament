package uz.softs.tasks.ui.adapters

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_item.view.*
import uz.softs.tasks.R
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.ItemClick
import uz.softs.tasks.util.extension.inflate
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.sorted
import kotlinx.android.synthetic.main.task_item_2.view.*
import kotlinx.android.synthetic.main.task_item_2.view.itemClick
import kotlinx.android.synthetic.main.task_item_2.view.symbol
import kotlinx.android.synthetic.main.task_item_2.view.textTaskDate
import kotlinx.android.synthetic.main.task_item_2.view.textTaskDegree
import kotlinx.android.synthetic.main.task_item_2.view.textTaskInfo
import kotlinx.android.synthetic.main.task_item_2.view.textTaskName
import kotlinx.android.synthetic.main.task_item_2.view.textTaskTime
import kotlin.collections.ArrayList

class AllTaskRecycleAdapter : RecyclerView.Adapter<AllTaskRecycleAdapter.ViewHolder>() {
    private val data = ArrayList<TaskData>()
    private var listener: ItemClick<TaskData>? = null
    private var listenerDone: ItemClick<TaskData>? = null
    private var listenerCanceled: ItemClick<TaskData>? = null
    private var listenerDelete: ItemClick<Int>? = null
//    private val differ = AsyncListDiffer(this, ITEM_DIFF)
//
//    companion object {
//        private val ITEM_DIFF = object : DiffUtil.ItemCallback<TaskData>() {
//            override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData) =
//                oldItem.id == newItem.id
//
//            override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData) =
//                oldItem.isDone == newItem.isDone && oldItem.isOutDated == newItem.isOutDated && oldItem.isCanceled == newItem.isCanceled
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            0 -> ViewHolder(parent.inflate(R.layout.task_item_2))
            1 -> ViewHolder(parent.inflate(R.layout.task_item))
            else -> ViewHolder(parent.inflate(R.layout.task_item))
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun getItemViewType(position: Int): Int {
        return if (!data[position].isOutDated && !data[position].isDone && !data[position].isCanceled && !data[position].isDelete) 0
        else 1
    }

    fun setOnItemClickListener(f: ItemClick<TaskData>) {
        listener = f
    }

    fun setOnItemDoneListener(f: ItemClick<TaskData>) {
        listenerDone = f
    }

    fun setOnItemCanceledListener(f: ItemClick<TaskData>) {
        listenerCanceled = f
    }

    fun setOnItemDeleteListener(f: ItemClick<Int>) {
        listenerDelete = f
    }

    fun submitList(list: List<TaskData>) {
        data.clear()
        data.addAll(sorted(list))
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.apply {
                itemClick?.setOnClickListener { listener?.invoke(data[adapterPosition]) }
                buttonMore?.setOnClickListener { view1 ->
                    val menu = PopupMenu(context, view1)
                    menu.inflate(R.menu.popup_menu)
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menuButtonDone -> {
                                listenerDone?.invoke(data[adapterPosition])
                                listenerDelete?.invoke(adapterPosition)
                            }
                            R.id.menuButtonCanceled -> {
                                listenerCanceled?.invoke(data[adapterPosition])
                                listenerDelete?.invoke(adapterPosition)
                            }
                        }
                        true
                    }
                    menu.show()
                }
            }
        }

        fun bind() {
            itemView.apply {
                this.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)
                val d = data[adapterPosition]
                textTaskName.text = d.name
                textTaskInfo.text = d.info
                textTaskDate.text = d.date
                textTaskTime.text = d.time
                textTaskDegree.text = d.degree.toString()
                if (!d.isOutDated && !d.isDone && !d.isCanceled && !d.isDelete) {
                    setColorToViews(R.color.colorBlue)
                    return
                }
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



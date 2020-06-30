package uz.softs.tasks.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_item.view.*
import uz.softs.tasks.R
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.ItemClick
import uz.softs.tasks.util.extension.inflate
import kotlinx.android.synthetic.main.task_item_2.view.*
import kotlinx.android.synthetic.main.task_item_2.view.itemClick
import kotlinx.android.synthetic.main.task_item_2.view.symbol
import kotlinx.android.synthetic.main.task_item_2.view.textTaskDate
import kotlinx.android.synthetic.main.task_item_2.view.textTaskDegree
import kotlinx.android.synthetic.main.task_item_2.view.textTaskInfo
import kotlinx.android.synthetic.main.task_item_2.view.textTaskName
import kotlinx.android.synthetic.main.task_item_2.view.textTaskTime


class EditTasksAdapter : RecyclerView.Adapter<EditTasksAdapter.ViewHolder>() {
    private var listener: ItemClick<TaskData>? = null
    private var listenerCanceled: ItemClick<TaskData>? = null
    private var listenerDelete: ItemClick<TaskData>? = null
    private var listenerEdit: ItemClick<TaskData>? = null
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
        differ.submitList(list)
    }

    fun setOnItemEditListener(f: ItemClick<TaskData>) {
        listenerEdit = f
    }

    fun setOnItemDeleteListener(f: ItemClick<TaskData>) {
        listenerDelete = f
    }

    fun setOnItemCanceledListener(f: ItemClick<TaskData>) {
        listenerCanceled = f
    }

    fun setOnItemClickListener(f: ItemClick<TaskData>) {
        listener = f
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.task_item_2))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.apply {
                itemClick.setOnClickListener {
                    listener?.invoke(differ.currentList[adapterPosition])
                }
                buttonMore?.setOnClickListener { view1 ->
                    val menu = PopupMenu(context, view1)
                    val d = differ.currentList[adapterPosition]
                    if (d.isCanceled || d.isOutDated || d.isDone) menu.inflate(R.menu.popup_menu_edit1)
                    else menu.inflate(R.menu.popup_menu_edit)
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menuButtonDelete -> {
                                listenerDelete?.invoke(differ.currentList[adapterPosition])
                            }
                            R.id.menuButtonCanceled -> {
                                listenerCanceled?.invoke(differ.currentList[adapterPosition])
                            }
                            R.id.menuButtonEdit -> {
                                listenerEdit?.invoke(differ.currentList[adapterPosition])
                                Log.d("TTT", differ.currentList[adapterPosition].id.toString())
                            }
                        }
                        true
                    }
                    menu.show()
                }
            }
        }

        @SuppressLint("ResourceAsColor")
        fun bind() {
            itemView.apply {
                this.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)
                val d = differ.currentList[adapterPosition]
                textTaskName.text = d.name
                textTaskInfo.text = d.info
                textTaskDate.text = d.date
                textTaskTime.text = d.time
                textTaskDegree.text = d.degree.toString()
                if (!d.isDone && !d.isCanceled && !d.isDelete && !d.isOutDated) {
                    setColorToViews(R.color.colorRed)
                    return
                }
                if (d.isDone && !d.isCanceled && !d.isDelete && !d.isOutDated) {
                    setColorToViews(R.color.colorGreen)
                    return
                }
                if (d.isOutDated && !d.isDone && !d.isCanceled && !d.isDelete) {
                    setColorToViews(R.color.colorOutDated)
                    return
                }
                if (d.isCanceled && !d.isDelete && !d.isOutDated && !d.isDone) {
                    setColorToViews(R.color.colorCanceled)
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
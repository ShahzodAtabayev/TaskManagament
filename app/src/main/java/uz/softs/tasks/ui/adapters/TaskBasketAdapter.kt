package uz.softs.tasks.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.softs.tasks.R
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.ItemClick
import uz.softs.tasks.util.extension.inflate
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.sorted
import kotlinx.android.synthetic.main.task_item_2.view.*


class TaskBasketAdapter : RecyclerView.Adapter<TaskBasketAdapter.ViewHolder>() {
    private var listener: ItemClick<TaskData>? = null
    private var listenerDelete: ItemClick<TaskData>? = null
    private var listenerRestore: ItemClick<TaskData>? = null
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.task_item_2))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    fun submitList(list: List<TaskData>) {
        differ.submitList(sorted(list))
    }

    fun setOnItemDeleteListener(f: ItemClick<TaskData>) {
        listenerDelete = f
    }

    fun setOnItemRestoreListener(f: ItemClick<TaskData>) {
        listenerRestore = f
    }

    fun setOnItemClickListener(f: ItemClick<TaskData>) {
        listener = f
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.apply {
                itemClick.setOnClickListener {
                    listener?.invoke(differ.currentList[adapterPosition])
                }
                buttonMore?.setOnClickListener { view1 ->
                    val menu = PopupMenu(context, view1)
                    menu.inflate(R.menu.popup_menu_delete)
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menuButtonDelete -> {
                                listenerDelete?.invoke(differ.currentList[adapterPosition])
                            }
                            R.id.menuButtonRestore -> {
                                listenerRestore?.invoke(differ.currentList[adapterPosition])
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
                this.animation= AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation)
                val d = differ.currentList[adapterPosition]
                symbol.setBackgroundColor(R.color.colorOutDated)
                textTaskDegree.setBackgroundColor(R.color.colorOutDated)
                textTaskName.text = d.name
                textTaskInfo.text = d.info
                textTaskDate.text = d.date
                textTaskTime.text = d.time
                textTaskDegree.text = d.degree.toString()
            }
        }
    }
}
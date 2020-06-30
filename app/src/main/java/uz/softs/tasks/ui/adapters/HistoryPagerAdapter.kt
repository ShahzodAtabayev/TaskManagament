package uz.softs.tasks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.source.model.FilteredSerializableData
import uz.softs.tasks.ui.fragments.HistoryFragment
import uz.softs.tasks.util.helpers.ItemClick
import uz.softs.tasks.util.extension.putArguments

class HistoryPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var data = ArrayList<List<TaskData>>()
    private var listener: ItemClick<TaskData>? = null
    fun submitData(list: List<List<TaskData>>) {
        data.clear()
        data.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    fun setOnItemClickListener(f: ItemClick<TaskData>) {
        listener = f
    }

    override fun getItemCount(): Int = data.size
    override fun createFragment(position: Int): Fragment = HistoryFragment().apply {
        this.setOnItemClickListener { listener?.invoke(it) }
    }.putArguments {
        val filter = FilteredSerializableData(data[position])
        putSerializable("DATA", filter)
    }
}


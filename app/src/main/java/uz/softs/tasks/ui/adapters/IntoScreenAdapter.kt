package uz.softs.tasks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.softs.tasks.data.source.model.IntoScreenData
import uz.softs.tasks.ui.fragments.IntoScreenFragment
import uz.softs.tasks.util.extension.putArguments

class IntoScreenAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val data = ArrayList<IntoScreenData>()
    fun submitList(list: List<IntoScreenData>) {
        data.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment = IntoScreenFragment().putArguments {
        putSerializable("DATA", data[position])
    }

}
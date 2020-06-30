package uz.softs.tasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import uz.softs.tasks.R
import uz.softs.tasks.apps.App
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.source.model.FilteredSerializableData
import uz.softs.tasks.ui.adapters.AllTaskRecycleAdapter
import uz.softs.tasks.util.helpers.ItemClick
import kotlinx.android.synthetic.main.pager_layout.*

class SeeAllTaskFragment : Fragment(R.layout.pager_layout) {
    private var adapter = AllTaskRecycleAdapter()
    private var listener: ItemClick<TaskData>? = null
    private var listenerDone: ItemClick<TaskData>? = null
    private var listenerCanceled: ItemClick<TaskData>? = null

    fun setOnItemClickListener(f: ItemClick<TaskData>) {
        listener = f
    }

    fun setOnItemDoneListener(f: ItemClick<TaskData>) {
        listenerDone = f
    }

    fun setOnItemCanceledListener(f: ItemClick<TaskData>) {
        listenerCanceled = f
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = requireArguments()
        val data = bundle.getSerializable("DATA") as FilteredSerializableData
        adapter.submitList(data.list)
        list_pager.layoutManager = LinearLayoutManager(App.instance)
        list_pager.adapter = adapter
        adapter.setOnItemClickListener { listener?.invoke(it) }
        adapter.setOnItemCanceledListener { listenerCanceled?.invoke(it) }
        adapter.setOnItemDeleteListener { adapter.removeItem(it)}
        adapter.setOnItemDoneListener { listenerDone?.invoke(it) }
    }
}
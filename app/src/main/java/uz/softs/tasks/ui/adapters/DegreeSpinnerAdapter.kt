package uz.softs.tasks.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import uz.softs.tasks.R
import uz.softs.tasks.data.source.model.DegreeData
import kotlinx.android.synthetic.main.layout_spinner.view.*


class DegreeSpinnerAdapter(
    ctx: Context,
    data: List<DegreeData>
) :
    ArrayAdapter<DegreeData>(ctx, 0, data) {
    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val mood = getItem(position)!!
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.layout_spinner,
            parent,
            false
        )
        view.moodImage.setImageResource(mood.icon)
        view.moodText.text = mood.text
        return view
    }
}
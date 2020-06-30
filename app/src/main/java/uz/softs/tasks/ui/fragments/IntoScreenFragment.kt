package uz.softs.tasks.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.softs.tasks.R
import uz.softs.tasks.data.source.model.IntoScreenData
import kotlinx.android.synthetic.main.into_pages.*

class IntoScreenFragment : Fragment(R.layout.into_pages) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireArguments()
        val data = bundle.getSerializable("DATA") as IntoScreenData
        text_title.text = data.title
        val bitmap = BitmapFactory.decodeResource(resources, data.image)
        image_background.setBackgroundResource(data.pathImage)
        image_into.setImageBitmap(bitmap)
        text_info.text = data.info
    }
}
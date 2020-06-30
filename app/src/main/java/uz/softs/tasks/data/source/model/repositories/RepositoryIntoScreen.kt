package uz.softs.tasks.data.source.model.repositories

import uz.softs.tasks.R
import uz.softs.tasks.apps.App
import uz.softs.tasks.contracts.ContractIntoScreen
import uz.softs.tasks.data.local.storage.LocalStorage
import uz.softs.tasks.data.source.model.IntoScreenData

class RepositoryIntoScreen : ContractIntoScreen.Model {
    private var data: ArrayList<IntoScreenData> = ArrayList()
    private val resources=App.instance.resources

    init {
        data.add(
            IntoScreenData(
              resources.getString(R.string.welcome),
                R.drawable.welcome_image,
                resources.getString(R.string.daily_work),
                R.drawable.path1
            )
        )
        data.add(IntoScreenData(resources.getString(R.string.availability), R.drawable.clock_image, resources.getString(R.string.save_time), R.drawable.path2))
        data.add(
            IntoScreenData(
                resources.getString(R.string.simplicity),
                R.drawable.comfortable_image,
                resources.getString(R.string.easier_to_plan)
                , R.drawable.path3
            )
        )
    }

    override fun getAll(): List<IntoScreenData> = data
    override fun setIsFirst(boolean: Boolean) {
        LocalStorage.instance.isFirst = boolean
    }
}
package uz.softs.tasks.contracts

import uz.softs.tasks.data.source.model.IntoScreenData

interface ContractIntoScreen {
    interface Model {
        fun getAll(): List<IntoScreenData>
        fun setIsFirst(boolean: Boolean)
    }

    interface View {
        fun loadData(list: List<IntoScreenData>)
        fun setCurrentItem(item: Int)
        fun openActivity()
        fun finishWindow()
    }

    interface Presenter {
        fun buttonNext(currentItem: Int)
    }
}
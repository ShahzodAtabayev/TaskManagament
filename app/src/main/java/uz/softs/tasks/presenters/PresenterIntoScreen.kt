package uz.softs.tasks.presenters

import uz.softs.tasks.contracts.ContractIntoScreen

class PresenterIntoScreen(private val model: ContractIntoScreen.Model, private val view: ContractIntoScreen.View) : ContractIntoScreen.Presenter {
    init {
        view.loadData(model.getAll())
    }

    override fun buttonNext(currentItem: Int) {
        if (currentItem != model.getAll().size - 1) {
            view.setCurrentItem(currentItem + 1)
        } else {
            model.setIsFirst(false)
            view.finishWindow()
            view.openActivity()
        }
    }
}
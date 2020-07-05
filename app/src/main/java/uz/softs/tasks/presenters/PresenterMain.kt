package uz.softs.tasks.presenters

import android.os.Handler
import android.os.Looper
import uz.softs.tasks.contracts.ContractMain
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.changeData
import java.util.concurrent.Executors

class PresenterMain(private val model: ContractMain.Model, private val view: ContractMain.View) :
    ContractMain.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    init {
        runOnWorkerThread {
            val ls = model.getAllTask()
            val data = changeData(ls)
            runOnUIThread {
                view.loadDataToView(data, model.getImageProfile(), model.getFullNameProfile(), model.isFirst())
                model.setIsFirst(false)
            }
        }
    }

    override fun initData() {
        runOnWorkerThread {
            runOnUIThread { view.setRefreshing(true) }
            Thread.sleep(800)
            val ls = model.getAllTask()
            runOnUIThread {
                view.loadDataToView(changeData(ls), model.getImageProfile(), model.getFullNameProfile(), model.isFirst())
                view.setRefreshing(false)
            }
        }
    }

    override fun clickItem(data: TaskData) {
        view.openItemDialog(data)
    }

    override fun buttonNavigationView() {
        view.openNavigation()
    }

    override fun openAddTask() {
        view.addTask()
    }

    override fun openSeeAllTask() {
        view.seeAllTask()
    }

    override fun openEditTask() {
        view.editTask()
    }

    override fun openHistory() {
        view.history()
    }

    override fun openTaskBasket() {
        view.taskBasket()
    }

    override fun openTermOfUse() {
        view.termOfUse()
    }

    override fun openInstructionToUse() {
        view.instructionToUse()
    }

    override fun openSettings() {
        view.settings()
    }

    override fun share() {
        view.share()
    }

    private fun runOnUIThread(f: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            f()
        } else {
            handle.post { f() }
        }
    }

    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }
}
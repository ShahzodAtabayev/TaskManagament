package uz.softs.tasks.presenters

import android.os.Handler
import android.os.Looper
import uz.softs.tasks.contracts.ContractEditTasks
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.changeData
import java.util.concurrent.Executors

class PresenterEditTask(
    private val model: ContractEditTasks.Model,
    private val view: ContractEditTasks.View
) : ContractEditTasks.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    init {
        runOnWorkerThread {
            val ls = model.getAll()
            runOnUIThread { view.loadData(changeData(ls)) }
        }
    }

    override fun clickItem(data: TaskData) {
        view.openItemDialog(data)
    }

    override fun buttonRemove(data: TaskData) {
        runOnWorkerThread {
            data.isDelete = true
            model.update(data)
            val ls = model.getAll()
            runOnUIThread { view.loadData(changeData(ls)) }
        }
    }

    override fun buttonCanceled(data: TaskData) {
        runOnWorkerThread {
            data.isCanceled = true
            model.update(data)
            val ls = model.getAll()
            runOnUIThread { view.loadData(changeData(ls)) }
        }
    }

    override fun buttonEdit(data: TaskData) {
        runOnWorkerThread {
            model.update(data)
            val ls = model.getAll()
            runOnUIThread {
                view.loadData(ls)
                view.cancelDialog()
            }
        }
    }

    override fun buttonEditPopupMenu(data: TaskData) {
        view.openEditDialog(data)
    }

    override fun buttonClose() {
        view.finishWindow()
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
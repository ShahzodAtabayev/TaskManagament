package uz.softs.tasks.presenters

import android.os.Handler
import android.os.Looper
import uz.softs.tasks.contracts.ContractTaskBasket
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.changeData
import java.util.concurrent.Executors

class PresenterTaskBasket(
    private val model: ContractTaskBasket.Model,
    private val view: ContractTaskBasket.View
) : ContractTaskBasket.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    init {
        runOnWorkerThread {
            changeData(model.getAll())
            val ls = model.getAll()
            runOnUIThread { view.loadData(ls) }
        }
    }

    override fun clickItem(data: TaskData) {
        view.openItemDialog(data)
    }

    override fun buttonRemove(data: TaskData) {
        runOnWorkerThread {
            model.delete(data)
            val ls = model.getAll()
            runOnUIThread { view.loadData(ls) }
        }
    }

    override fun buttonRestore(data: TaskData) {
        runOnWorkerThread {
            data.isDelete = false
            model.update(data)
            val ls = model.getAll()
            runOnUIThread { view.loadData(ls) }
        }
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
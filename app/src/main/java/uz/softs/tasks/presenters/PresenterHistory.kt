package uz.softs.tasks.presenters

import android.os.Handler
import android.os.Looper
import uz.softs.tasks.contracts.ContractHistory
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.HelperChangeData
import java.util.concurrent.Executors

class PresenterHistory(
    private val model: ContractHistory.Model,
    private val view: ContractHistory.View
) : ContractHistory.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    init {
        runOnWorkerThread {
            val ls = model.getAll()
            runOnUIThread { view.loadData(ls) }
        }
    }

    override fun clickItem(data: TaskData) {
        view.openItemDialog(data)
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
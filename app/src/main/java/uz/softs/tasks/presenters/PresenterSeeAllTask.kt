package uz.softs.tasks.presenters

import android.os.Handler
import android.os.Looper
import uz.softs.tasks.contracts.ContractSeeAllTask
import uz.softs.tasks.data.local.room.entity.TaskData
import java.util.concurrent.Executors

class PresenterSeeAllTask(
    private val model: ContractSeeAllTask.Model,
    private val view: ContractSeeAllTask.View
) : ContractSeeAllTask.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    init {
        runOnWorkerThread {
            val ls = model.getAllTask()
            runOnUIThread { view.loadDataToView(ls) }
        }
    }

    override fun clickItem(data: TaskData) {
        view.openItemDialog(data)
    }

    override fun buttonDone(taskData: TaskData) {
        runOnWorkerThread {
            taskData.isDone = true
            model.update(taskData)
            val ls = model.getAllTask()
            runOnUIThread { view.loadDataToView(ls) }
        }
    }

    override fun buttonCanceled(taskData: TaskData) {
        runOnWorkerThread {
            taskData.isCanceled = true
            model.update(taskData)
            val ls = model.getAllTask()
            runOnUIThread { view.loadDataToView(ls) }
        }
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
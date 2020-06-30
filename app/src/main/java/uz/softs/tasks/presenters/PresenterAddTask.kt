package uz.softs.tasks.presenters

import android.os.Handler
import android.os.Looper
import uz.softs.tasks.R
import uz.softs.tasks.apps.App
import uz.softs.tasks.contracts.ContractAddTask
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.util.helpers.HelperChangeData.Companion.changeData
import java.util.concurrent.Executors

class PresenterAddTask(
    private val model: ContractAddTask.Model,
    private val view: ContractAddTask.View
) : ContractAddTask.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())
    private val context = view.getContext()

    override fun addTask() {
        view.changeEditTextBackground()
        if (view.getName().isEmpty()) {
            view.makeToast(context.resources.getString(R.string.toast1))
            view.changeErrorBackground(1)
            return
        }
        if (view.getInfo().isEmpty()) {
            view.makeToast(context.resources.getString(R.string.toast2))
            view.changeErrorBackground(2)
            return
        }
        if (view.getDate().isEmpty()) {
            view.makeToast(context.resources.getString(R.string.toast3))
            view.changeErrorBackground(3)
            return
        }
        if (view.getTime().isEmpty()) {
            view.makeToast(context.resources.getString(R.string.toast4))
            view.changeErrorBackground(4)
            return
        }
        runOnWorkerThread {
            model.insert(
                TaskData(
                    name = view.getName(),
                    info = view.getInfo(),
                    date = view.getDate(),
                    time = view.getTime(),
                    degree = view.getDegree().toByte()
                )
            )
            changeData(model.getAll())
            runOnUIThread { view.finishAddTask() }
        }
    }

    override fun buttonClose() {
        view.finishAddTask()
    }

    override fun clickDateDialog() {
        view.openDateDialog()
    }

    override fun clickTimeDialog() {
        view.openTimeDialog()
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
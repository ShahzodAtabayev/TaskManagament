package uz.softs.tasks.ui.screens

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.BitmapFactory.decodeFile
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.android.synthetic.main.header_layout.view.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence
import uk.co.samuelwall.materialtaptargetprompt.extras.PromptText
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.CirclePromptFocal
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal
import uz.softs.tasks.R
import uz.softs.tasks.contracts.ContractMain
import uz.softs.tasks.data.local.room.entity.TaskData
import uz.softs.tasks.data.local.storage.LocalStorage
import uz.softs.tasks.data.source.model.repositories.RepositoryMain
import uz.softs.tasks.presenters.PresenterMain
import uz.softs.tasks.ui.adapters.MainAdapter
import uz.softs.tasks.ui.dialogs.ItemDialog
import uz.softs.tasks.util.extension.changeNavigationBarColor
import uz.softs.tasks.util.extension.changeStatusBarColor
import uz.softs.tasks.util.extension.toDarkenColor
import java.io.File


class MainActivity : LocaleAwareCompatActivity(), ContractMain.View {
    private lateinit var presenter: ContractMain.Presenter
    private var repository = RepositoryMain()
    private val adapter = MainAdapter()


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadView()
        presenter = PresenterMain(repository, this)
    }

    @SuppressLint("SdCardPath")
    private fun loadView() {
        buttonOpenNavigation.setOnClickListener { presenter.buttonNavigationView() }
        buttonAdd.setOnClickListener { presenter.openAddTask() }
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuSeeAllTask -> presenter.openSeeAllTask()
                R.id.menuEditTask -> presenter.openEditTask()
                R.id.menuHistory -> presenter.openHistory()
                R.id.menuTaskBasket -> presenter.openTaskBasket()
                R.id.menuSettings -> presenter.openSettings()
                R.id.menuTermOfUse -> presenter.openTermOfUse()
                R.id.menuInstructionToUse -> presenter.openInstructionToUse()
                R.id.menuShare -> presenter.share()
            }
            it.isCheckable = false
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        adapter.setOnItemClickListener { presenter.clickItem(it) }
        swipeRefresh.setOnRefreshListener { presenter.initData() }
        changeStatusBarColor(Color.parseColor("#34CDF6").toDarkenColor())
        changeNavigationBarColor(Color.parseColor("#34CDF6").toDarkenColor())
    }

    override fun loadDataToView(list: List<TaskData>, image: String, fullName: String, isFirst: Boolean) {
        adapter.submitList(list)
        if (isFirst){
            loadMainScreenTutorial()
        }
        val nav = navigationView.getHeaderView(0)
        nav.imageProfile.setImageBitmap(decodeFile(image))
        nav.textFullName.text = fullName
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "NotifyTask"
            val description = "Channel for tasks reminder"
            val channel = NotificationChannel("notifyTask", name, NotificationManager.IMPORTANCE_HIGH)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun loadMainScreenTutorial() {
        val ls = MaterialTapTargetSequence()
        ls.addPrompt(
            MaterialTapTargetPrompt.Builder(this@MainActivity)
                .setTarget(R.id.buttonAdd)
                .setBackButtonDismissEnabled(true)
                .setPrimaryText(R.string.tapTarget1)
                .setSecondaryText(R.string.tapTarget1_2)
                .setIcon(R.drawable.ic_add)
        )
        ls.addPrompt(
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.buttonOpenNavigation)
                .setBackButtonDismissEnabled(true)
                .setPrimaryText(R.string.tapTarget2)
                .setSecondaryText(R.string.tapTarget2_1)
                .setIcon(R.drawable.ic_menu)
        )

//        ls.addPrompt(
//            MaterialTapTargetPrompt.Builder(this)
//                .setTarget(nav.textFullName)
//                .setPromptBackground(RectanglePromptBackground())
//                .setPromptFocal(RectanglePromptFocal())
//                .setBackButtonDismissEnabled(true)
//                .setPrimaryText("It is full name")
//                .setSecondaryText("you enter your full name by entering the parameter for yourself.")
//        )
        /*ls.addPrompt(
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.menuSeeAllTask)
                .setBackButtonDismissEnabled(true)
                .setPrimaryText("All tasks")
                .setSecondaryText("You can see all the tasks here. You can end or cancel tasks that are not on time.")
        )
        ls.addPrompt(
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.menuEditTask)
                .setBackButtonDismissEnabled(true)
                .setPrimaryText("Edit tasks")
                .setSecondaryText("You can edit tasks here. You can delete or cancel tasks that are not on time. The rest you can just delete or edit.")
        )*/
        ls.show()
        ls.setSequenceCompleteListener {
            val dialog = AlertDialog.Builder(this).create()
            dialog.setTitle("Hello")
            dialog.setMessage("Do you want to read instruction for use?")
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { dialogInterface, _ ->
                startActivity(Intent(this, InstructionToUseActivity::class.java))
                dialogInterface.dismiss()
            }
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            dialog.window?.setWindowAnimations(R.style.Animation_Design_BottomSheetDialog)
            dialog.show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun openNavigation() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun addTask() {
        startActivity(Intent(this, AddTaskActivity::class.java))
    }

    override fun seeAllTask() {
        startActivity(Intent(this, AllTaskActivity::class.java))
    }

    override fun editTask() {
        startActivity(Intent(this, EditTasksActivity::class.java))
    }

    override fun history() {
        startActivity(Intent(this, HistoryActivity::class.java))
    }

    override fun taskBasket() {
        startActivity(Intent(this, TaskBasketActivity::class.java))
    }

    override fun termOfUse() {
        startActivity(Intent(this, TermsOfUseActivity::class.java))
    }

    override fun instructionToUse() {
        startActivity(Intent(this, InstructionToUseActivity::class.java))
    }

    override fun settings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    override fun openItemDialog(data: TaskData) {
        val dialog = ItemDialog(this)
        dialog.loadData(data)
        dialog.show()
    }

    override fun setRefreshing(boolean: Boolean) {
        swipeRefresh.isRefreshing = boolean
    }

    override fun share() {
        try {
            val ai = packageManager.getApplicationInfo(packageName, 0)
            val srcFile = File(ai.publicSourceDir)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            val uri: Uri = FileProvider.getUriForFile(this, packageName, srcFile)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            grantUriPermission(
                packageManager.toString(), uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRestart() {
        super.onRestart()
        presenter.initData()
    }

}

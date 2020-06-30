package uz.softs.tasks.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.PopupMenu
import uz.softs.tasks.R
import uz.softs.tasks.data.local.storage.LocalStorage
import uz.softs.tasks.ui.dialogs.ChangeLanguageDialog
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import com.zeugmasolutions.localehelper.LocaleHelper
import id.zelory.compressor.saveBitmap
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.item_dialog_edit_full_name.view.*
import java.io.File
import java.io.IOException
import java.util.*


class SettingsActivity : LocaleAwareCompatActivity() {
    private val localStorage = LocalStorage.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        loadView()
    }


    @SuppressLint("InflateParams")
    private fun loadView() {
        buttonCloseSettings.setOnClickListener {
            finish()
        }
        textFullNameSettings.text = localStorage.profileFullName
        imageProfileSettings.setImageBitmap(BitmapFactory.decodeFile(localStorage.profileImage))
        buttonMoreSettings.setOnClickListener { view ->
            val menu = PopupMenu(this, view)
            menu.inflate(R.menu.popup_menu_edit_profile)
            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menuButtonEditFullName -> editFullName()
                    R.id.menuButtonChangeProfileImage -> pickImageFromGallery()
                }
                true
            }
            menu.show()
        }
        buttonChangeLanguage.setOnClickListener {
            val dialog = ChangeLanguageDialog(this, resources)
            val a=LocaleHelper.getLocale(this)
            dialog.checkRadio(when(a.language){
                "uz"->R.id.rb1
                "fr"->R.id.rb2
                "ru"->R.id.rb3
                "en"->R.id.rb4
                else->R.id.rb4
            })
            dialog.confirm { language, checkId ->
                updateLocale(Locale(language))
                localStorage.checked = checkId
            }
            dialog.show()
        }
    }

    private fun editFullName() {
        val dialog = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.item_dialog_edit_full_name, null, false)
        dialog.setView(view)
        dialog.setTitle(resources.getString(R.string.Edit))
        view.editFullNameDialog.setText(localStorage.profileFullName)
        dialog.setNegativeButton(resources.getString(R.string.cancel)) { dialog1, _ ->
            dialog1.cancel()
        }
        dialog.setPositiveButton("Ok") { dialog1, _ ->
            localStorage.profileFullName = view.editFullNameDialog.text.toString()
            textFullNameSettings.text = view.editFullNameDialog.text.toString()
            dialog1.cancel()
        }
        dialog.show()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1
    }


    @SuppressLint("SdCardPath", "Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val selectedImage = data!!.data
            tempImage.setImageURI(selectedImage)
            val bit = tempImage.drawable as BitmapDrawable
            val bitmap = resizedBitmap(bit.bitmap, 512, 512)
            imageProfileSettings.setImageBitmap(bitmap)
            try {
                val directory = File("/data/data/uz.softs.tasks/", "files")
                if (!directory.exists()) directory.mkdir()
                val file = File("/data/data/uz.softs.tasks/files/", "profileImage.png")
                if (!file.exists()) file.createNewFile()
                saveBitmap(bitmap, file)
                localStorage.profileImage = file.absolutePath
            } catch (d: IOException) {
                d.printStackTrace()
            }
        }
    }

    private fun resizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(
            bm, 0, 0, width, height,
            matrix, false
        )
    }
}
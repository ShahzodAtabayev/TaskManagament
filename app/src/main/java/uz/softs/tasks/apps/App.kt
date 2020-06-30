package uz.softs.tasks.apps

import com.zeugmasolutions.localehelper.LocaleAwareApplication
import uz.softs.tasks.data.local.storage.LocalStorage

class App: LocaleAwareApplication(){
    override fun onCreate() {
        super.onCreate()
        instance =this
        LocalStorage.init(this)

    }
    companion object{
        lateinit var instance: App
    }
}
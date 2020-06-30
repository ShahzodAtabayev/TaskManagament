package uz.softs.tasks.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class TaskData(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0,
    var name:String,
    var info:String,
    var date:String,
    var time:String,
    var degree:Byte,
    var isDone:Boolean=false,
    var isCanceled:Boolean=false,
    var isOutDated:Boolean=false,
    var isDelete:Boolean=false
):Serializable
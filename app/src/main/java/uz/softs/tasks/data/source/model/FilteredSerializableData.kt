package uz.softs.tasks.data.source.model

import uz.softs.tasks.data.local.room.entity.TaskData
import java.io.Serializable

data class FilteredSerializableData(var list: List<TaskData>) : Serializable
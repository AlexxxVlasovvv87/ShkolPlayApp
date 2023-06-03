package ru.alexvlasov.shkolplay.presentation.features.teacher.rate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.alexvlasov.shkolplay.data.room.repositories.GroupRepository
import ru.alexvlasov.shkolplay.domain.model.Achievement
import ru.alexvlasov.shkolplay.presentation.utils.launchIO
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val repository: GroupRepository,
) : ViewModel() {

    private var _modelList = MutableStateFlow<List<Achievement>>(emptyList())
    val modelList = _modelList.asStateFlow()
    var loading by mutableStateOf(false)
    var isSearchOpened by mutableStateOf(false)
    var searchText by mutableStateOf("")
    var name by mutableStateOf("")
    var isDialogOpened by mutableStateOf(false)

    fun fetch(id: UUID?) {
        launchIO {
            val flow = if (id != null )
                repository.getAchievements(id)
            else
                repository.getAchievements()
            flow.distinctUntilChanged().collect { _modelList.value = it }

        }
    }

    fun deleteFromGroup(id: UUID, userId: UUID) {
        launchIO {
            repository.deleteFromGroup(id, userId)
            fetch(id)
        }
    }

    fun filteredIsEmpty() = modelList.value.none { isFiltered(it) }
    fun isFiltered(model: Achievement?) = model != null && model.user!!.fullName().lowercase().contains(searchText)

    fun add(
        id: UUID,
        onSuccess: suspend () -> Unit,
        onError: suspend (msg: String) -> Unit,
    ) = launchIO {
        repository.addStudent(
            id = id,
            email = name,
            onError = { onError("Сетевая ошибка") },
            onSuccess = onSuccess
        )
        repository.getAchievements(id).distinctUntilChanged().collect { _modelList.value = it }
    }
}


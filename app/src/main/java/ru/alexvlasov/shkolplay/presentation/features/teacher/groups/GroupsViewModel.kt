package ru.alexvlasov.shkolplay.presentation.features.teacher.groups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.alexvlasov.shkolplay.data.room.repositories.GroupRepository
import ru.alexvlasov.shkolplay.domain.model.Group
import ru.alexvlasov.shkolplay.presentation.utils.launchIO
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val repository: GroupRepository,
) : ViewModel() {

    private var _modelList = MutableStateFlow<List<Group>>(emptyList())
    val modelList = _modelList.asStateFlow()
    var loading by mutableStateOf(false)
    var isSearchOpened by mutableStateOf(false)
    var searchText by mutableStateOf("")
    var name by mutableStateOf("")
    var isDialogOpened by mutableStateOf(false)


    fun fetchGroups() {
        launchIO {
            repository.get().distinctUntilChanged().collect { _modelList.value = it }
        }
    }
    fun filteredIsEmpty() = modelList.value.none { isFiltered(it) }
    fun isFiltered(model: Group?) = model != null && model.groupName.lowercase().contains(searchText)
    fun update(model: Group) = launchIO {
        repository.update(model)
        repository.get().distinctUntilChanged().collect { _modelList.value = it }
    }
    fun delete(id: UUID) = launchIO {
        repository.delete(id)
        repository.get().distinctUntilChanged().collect { _modelList.value = it }
    }
    fun add(
        onSuccess: suspend () -> Unit,
        onError: suspend (msg: String) -> Unit,
    ) = launchIO {
        repository.add(
            groupName = name,
            onError = { onError("Сетевая ошибка") },
            onSuccess = onSuccess
        )
        repository.get().distinctUntilChanged().collect { _modelList.value = it }
    }
}


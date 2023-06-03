package ru.alexvlasov.shkolplay.presentation.features.teacher.teacherslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.alexvlasov.shkolplay.data.room.repositories.UserRepository
import ru.alexvlasov.shkolplay.domain.model.User
import ru.alexvlasov.shkolplay.presentation.utils.launchIO
import javax.inject.Inject

@HiltViewModel
class TeachersViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private var _modelList = MutableStateFlow<List<User>>(emptyList())
    val modelList = _modelList.asStateFlow()
    var loading by mutableStateOf(false)
    var isSearchOpened by mutableStateOf(false)
    var searchText by mutableStateOf("")

    fun fetch() {
        launchIO {
            repository.getTeachers().distinctUntilChanged().collect { _modelList.value = it }

        }
    }
    fun filteredIsEmpty() = modelList.value.none { isFiltered(it) }
    fun isFiltered(model: User?) = model != null && model.fullName().lowercase().contains(searchText)

}


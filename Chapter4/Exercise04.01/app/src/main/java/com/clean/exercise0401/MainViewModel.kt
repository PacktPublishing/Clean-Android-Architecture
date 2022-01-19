package com.clean.exercise0401

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao,
    private val appDataStore: AppDataStore,
    private val mainTextFormatter: MainTextFormatter
) : ViewModel() {

    private val _uiStateLiveData = MutableLiveData(UiState())
    val uiStateLiveData: LiveData<UiState> = _uiStateLiveData

    init {
        viewModelScope.launch {
            flow { emit(userService.getUsers()) }
                .onEach {
                    val userEntities =
                        it.map { user -> UserEntity(user.id, user.name, user.username, user.email) }
                    userDao.insertUsers(userEntities)
                    appDataStore.incrementCount()
                }.flatMapConcat { userDao.getUsers() }
                .catch { emitAll(userDao.getUsers()) }
                .flatMapConcat { users ->
                    appDataStore.savedCount.map { count ->
                        UiState(
                            users,
                            mainTextFormatter.getCounterText(count)
                        )
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiStateLiveData.value = it
                }
        }
    }
}

data class UiState(
    val userList: List<UserEntity> = listOf(),
    val count: String = ""
)
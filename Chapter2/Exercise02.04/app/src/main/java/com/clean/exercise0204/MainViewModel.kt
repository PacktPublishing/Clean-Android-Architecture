package com.clean.exercise0204

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val userService: UserService,
    private val userDao: UserDao
) : ViewModel() {

    var resultState by mutableStateOf<List<UserEntity>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            flow { emit(userService.getUsers()) }
                .onEach {
                val userEntities =
                    it.map { user -> UserEntity(user.id, user.name, user.username, user.email) }
                userDao.insertUsers(userEntities)
            }.flatMapConcat { userDao.getUsers() }
                .catch { emitAll(userDao.getUsers()) }
                .flowOn(Dispatchers.IO)
                .collect {
                    resultState = it
                }
        }
    }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(MyApplication.userService, MyApplication.userDao) as T
}
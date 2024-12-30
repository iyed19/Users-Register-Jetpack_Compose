package com.example.usersregistercompose.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val database = Room.databaseBuilder(
        application.applicationContext,
        UserRoomDB::class.java,
        "users_DB"
    ).build()

    private val userDao = database.userDao()

    var inputName by mutableStateOf("")
        private set

    var inputEmail by mutableStateOf("")
        private set

    var ListofUsers = mutableStateListOf<User>()
        private set

    var selectedUser: User? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val users = userDao.getAllUsers()
            ListofUsers.addAll(users)
        }
    }

    fun onNameChange(newName: String) {
        inputName = newName
    }

    fun onEmailChange(newEmail: String) {
        inputEmail = newEmail
    }

    fun selectUser(user: User) {
        selectedUser = user
        inputName = user.name
        inputEmail = user.email
    }

    fun clearSelection() {
        selectedUser = null
        inputName = ""
        inputEmail = ""
    }



    fun addUser() {
        val user = User(name = inputName, email = inputEmail)
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(user)
            val updatedUsers = userDao.getAllUsers()
            ListofUsers.clear()
            ListofUsers.addAll(updatedUsers)
        }
        inputName = ""
        inputEmail = ""
    }

    fun updateUser() {
        selectedUser?.let { user ->
            val updatedUser = user.copy(name = inputName, email = inputEmail)
            viewModelScope.launch(Dispatchers.IO) {
                userDao.updateUser(updatedUser)
                val updatedUsers = userDao.getAllUsers()
                ListofUsers.clear()
                ListofUsers.addAll(updatedUsers)
            }
            clearSelection()
        }
    }

    fun deleteUser() {
        selectedUser?.let { user ->
            viewModelScope.launch(Dispatchers.IO) {
                userDao.deleteUser(user)
                val updatedUsers = userDao.getAllUsers()
                ListofUsers.clear()
                ListofUsers.addAll(updatedUsers)
            }
            clearSelection()
        }
    }


    fun clearFields(){
        inputName = ""
        inputEmail = ""
    }
}

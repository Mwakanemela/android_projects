package com.example.coroutinesdemo.ViewModelScopeDemo

import kotlinx.coroutines.delay

class UserRepository {

    suspend fun getUsers(): List<User> {
        delay(8000)
        val users : List<User> = listOf(
            User(1, "Mwakanemela"),
            User(2, "Sibongire"),
            User(3, "Angellah"),
            User(4, "Joshua"),
        )

        return users
    }
}
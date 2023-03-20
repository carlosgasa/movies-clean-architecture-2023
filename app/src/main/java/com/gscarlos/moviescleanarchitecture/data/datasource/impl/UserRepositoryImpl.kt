package com.gscarlos.moviescleanarchitecture.data.datasource.impl

import com.gscarlos.moviescleanarchitecture.data.datasource.UserRepository
import com.gscarlos.moviescleanarchitecture.domain.model.User
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl : UserRepository {
    override fun getLocalUser() = flow {
        //Cargar usuario de prueba
        User(
            "https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            "12-02-1999"
        ).let {
            emit(it)
        }
    }
}
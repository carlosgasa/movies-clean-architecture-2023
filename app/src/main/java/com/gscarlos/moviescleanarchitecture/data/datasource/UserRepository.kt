package com.gscarlos.moviescleanarchitecture.data.datasource

import com.gscarlos.moviescleanarchitecture.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getLocalUser() : Flow<User>
}
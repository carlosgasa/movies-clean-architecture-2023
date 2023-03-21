package com.gscarlos.moviescleanarchitecture.domain.usecases.impl

import com.gscarlos.moviescleanarchitecture.data.local.database.AppDatabase
import com.gscarlos.moviescleanarchitecture.domain.usecases.UpdateRateUseCase
import javax.inject.Inject

class UpdateRateUseCaseImpl @Inject constructor(
    private val db: AppDatabase,
) : UpdateRateUseCase {


    override fun setRate(id: Int, newRate: Float) {
        db.movieDao().setRate(id, newRate * 2)
    }
}
package com.gscarlos.moviescleanarchitecture.domain.usecases

interface UpdateRateUseCase {
    fun setRate(id: Int, newRate: Float)
}
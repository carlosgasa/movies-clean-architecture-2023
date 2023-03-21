package com.gscarlos.moviescleanarchitecture.ui.images

import com.gscarlos.moviescleanarchitecture.domain.model.FileToShow


sealed class ImagesViewState {
    object Start: ImagesViewState()
    object Loading : ImagesViewState()
    object Error : ImagesViewState()
    class Success(var files: List<FileToShow>) : ImagesViewState()
}
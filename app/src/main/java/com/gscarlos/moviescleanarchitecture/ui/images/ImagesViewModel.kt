package com.gscarlos.moviescleanarchitecture.ui.images

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gscarlos.moviescleanarchitecture.data.datasource.FilesRepository
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.impl.OnUploadFileListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val filesRepository: FilesRepository
) : ViewModel() {


    private val _filesState = MutableStateFlow<ImagesViewState>(ImagesViewState.Start)
    val filesState = _filesState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    init {
        loadFilesFromFirebase()
    }

    private fun loadFilesFromFirebase() {
        viewModelScope.launch {
            _loadingState.value = true
            filesRepository.getFiles().collectLatest { result ->
                _loadingState.value = false
                _filesState.value = ImagesViewState.Success(result)
            }
        }
    }

    fun uploadFile(file: Uri) {
        _loadingState.value = true
        viewModelScope.launch {
            filesRepository.uploadFile(file) {
                _loadingState.value = false
                when(it){
                    OnUploadFileListener.OnError -> {}
                    OnUploadFileListener.OnSuccess -> loadFilesFromFirebase()
                }
            }
        }
    }
}
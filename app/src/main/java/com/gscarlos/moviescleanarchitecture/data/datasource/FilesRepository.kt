package com.gscarlos.moviescleanarchitecture.data.datasource

import android.net.Uri
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.impl.OnUploadFileListener
import com.gscarlos.moviescleanarchitecture.domain.model.FileToShow
import kotlinx.coroutines.flow.Flow

interface FilesRepository {
    suspend fun getFiles() : Flow<List<FileToShow>>
    suspend fun uploadFile(file: Uri, listener: (OnUploadFileListener) -> Unit)
}
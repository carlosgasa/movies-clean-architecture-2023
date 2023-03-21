package com.gscarlos.moviescleanarchitecture.data.datasource.impl

import android.net.Uri
import com.gscarlos.moviescleanarchitecture.data.datasource.FilesRepository
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.FirebaseProvider
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.impl.OnUploadFileListener
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.model.FileDto
import com.gscarlos.moviescleanarchitecture.data.toShow
import com.gscarlos.moviescleanarchitecture.domain.model.FileToShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(
    private val firebaseProvider: FirebaseProvider
) : FilesRepository {
    override suspend fun getFiles(): Flow<List<FileToShow>> = flow {
        val list = mutableListOf<FileToShow>()
        firebaseProvider.getFiles().await().let {
            for (document in it.documents) {
                try {
                    document.toObject(FileDto::class.java)?.let { doc ->
                        list.add(doc.toShow())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        emit(list)
    }

    override suspend fun uploadFile(file: Uri, listener: (OnUploadFileListener) -> Unit) {
        firebaseProvider.uploadFile(file, listener)
    }
}
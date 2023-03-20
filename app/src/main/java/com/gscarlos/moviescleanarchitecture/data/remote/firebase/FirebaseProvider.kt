package com.gscarlos.moviescleanarchitecture.data.remote.firebase

import android.location.Location
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.impl.OnUploadFileListener

interface FirebaseProvider {
    fun saveLocationGPS(location: Location?)
    fun getGPSHistory(): Task<QuerySnapshot>
    fun getFiles(): Task<QuerySnapshot>
    fun uploadFile(file: Uri, listener: (OnUploadFileListener) -> Unit)
}

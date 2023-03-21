package com.gscarlos.moviescleanarchitecture.data.remote.firebase.impl

import android.location.Location
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.FirebaseProvider
import java.util.*

sealed class OnUploadFileListener {
    object OnSuccess : OnUploadFileListener()
    object OnError : OnUploadFileListener()

}

class FirebaseProviderImpl : FirebaseProvider {

    private val REF_GPS_HISTORY = "HISTORIAL_GPS"
    private val REF_FILES = "IMAGENES"

    private val mRef = FirebaseFirestore.getInstance()

    override fun saveLocationGPS(location: Location?) {

        val l = hashMapOf(
            "longitud" to location?.longitude,
            "latitud" to location?.latitude,
            "fecha" to FieldValue.serverTimestamp()
        )
        mRef.collection(REF_GPS_HISTORY).add(l)
    }

    override fun getGPSHistory(): Task<QuerySnapshot> {
        return mRef.collection(REF_GPS_HISTORY).get()
    }

    override fun getFiles(): Task<QuerySnapshot> {
        return mRef.collection(REF_FILES).get()
    }

    override fun uploadFile(file: Uri, listener: (OnUploadFileListener) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val ref = storage.reference.child("files/${Date().time}")

        val uploadTask = ref.putFile(file)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    listener(OnUploadFileListener.OnError)
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val _file = hashMapOf(
                    "url" to task.result.toString(),
                    "fecha" to FieldValue.serverTimestamp()
                )
                mRef.collection(REF_FILES).add(_file).addOnCompleteListener {
                    if (it.isSuccessful) {
                        listener(OnUploadFileListener.OnSuccess)

                    } else {
                        listener(OnUploadFileListener.OnError)
                    }
                }
            } else {
                listener(OnUploadFileListener.OnError)
            }
        }
    }


}
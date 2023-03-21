package com.gscarlos.moviescleanarchitecture.ui.images.choose

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.databinding.DialogChooseBinding

class DialogChooseSourceImage private constructor(
    private val context: Context,
    private val layoutInflater: LayoutInflater,

    ) {

    class Builder(val context: Context) {

        private lateinit var layoutInflater: LayoutInflater

        fun setLayout(layoutInflater: LayoutInflater): Builder {
            this.layoutInflater = layoutInflater
            return this
        }

        fun build() =
            DialogChooseSourceImage(context, layoutInflater)

    }

    @SuppressLint("InflateParams")
    fun show(onCamera: () -> Unit, onGallery: () -> Unit) {
        DialogChooseBinding.bind(layoutInflater.inflate(R.layout.dialog_choose, null))
            .let { dialogBinding ->
                MaterialAlertDialogBuilder(context).setView(dialogBinding.root).create().let { dialog ->
                    dialogBinding.tvCamera.setOnClickListener {
                        onCamera()
                        dialog.dismiss()
                    }
                    dialogBinding.tvGallery.setOnClickListener {
                        onGallery()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
    }

}
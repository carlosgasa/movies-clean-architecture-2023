package com.gscarlos.moviescleanarchitecture.common.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtils {

    fun load(imageView: ImageView, source: String) {
        Glide.with(imageView).load(source).into(imageView)
    }

}
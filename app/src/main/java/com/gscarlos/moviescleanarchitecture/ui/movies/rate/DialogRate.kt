package com.gscarlos.moviescleanarchitecture.ui.movies.rate

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.common.Constants
import com.gscarlos.moviescleanarchitecture.common.utils.ImageUtils
import com.gscarlos.moviescleanarchitecture.databinding.DialogRateBinding
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow

class DialogRate private constructor(
    private val context: Context,
    private val movie: MovieToShow,
    private val layoutInflater: LayoutInflater
) {

    class Builder(val context: Context) {
        private lateinit var movie: MovieToShow
        private lateinit var layoutInflater: LayoutInflater

        fun setLayout(layoutInflater: LayoutInflater): Builder {
            this.layoutInflater = layoutInflater
            return this
        }

        fun setMovie(movie: MovieToShow): Builder {
            this.movie = movie
            return this
        }

        fun build() = DialogRate(context, movie, layoutInflater)
    }

    @SuppressLint("InflateParams")
    fun show(onRate: (Float) -> Unit) {
        DialogRateBinding.bind(layoutInflater.inflate(R.layout.dialog_rate, null))
            .let { dialogBinding ->
                ImageUtils.load(
                    dialogBinding.imgCover,
                    "${Constants.BASE_MOVIE_IMAGE_URL}w185/${movie.posterPath}"
                )
                dialogBinding.tvDescription.text = movie.overview
                dialogBinding.tvTitle.text = movie.title
                dialogBinding.ratingBar.rating =
                    if (movie.myRate == 0) 0f else movie.myRate.toFloat() / 2

                MaterialAlertDialogBuilder(context).setView(dialogBinding.root).create().let { dialog ->
                    dialogBinding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                        onRate(rating)
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
    }

}
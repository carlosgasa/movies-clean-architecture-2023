package com.gscarlos.moviescleanarchitecture.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.common.Constants
import com.gscarlos.moviescleanarchitecture.common.utils.ImageUtils
import com.gscarlos.moviescleanarchitecture.databinding.ItemMovieBinding
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow

class MoviesAdapter(private val listener: (MovieToShow) -> Unit) :
    ListAdapter<MovieToShow, MoviesAdapter.MovieViewHolder>(
        object : DiffUtil.ItemCallback<MovieToShow>() {
            override fun areItemsTheSame(oldItem: MovieToShow, newItem: MovieToShow): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieToShow, newItem: MovieToShow): Boolean =
                oldItem == newItem

            override fun getChangePayload(oldItem: MovieToShow, newItem: MovieToShow): Any? {
                if (oldItem.isFavorite != newItem.isFavorite) return newItem.isFavorite
                return null
            }
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return parent.inflate(R.layout.item_movie, false)
            .let { view -> ItemMovieBinding.bind(view) }
            .let { binding -> MovieViewHolder(binding) }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        val payload = payloads[0]
        if (payload is Boolean) holder.setIsFavorite(payload)
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieToShow) = with(binding) {
            ImageUtils.load(imgCover, "${Constants.BASE_MOVIE_IMAGE_URL}w185/${item.posterPath}")
            txtTitle.text = buildSpannedString {
                bold { append("${itemView.context.getString(R.string.lbl_title)}: ") }
                append(item.title)
            }
            txtVoteAverage.text = buildSpannedString {
                bold { append("${itemView.context.getString(R.string.lbl_vote_average)}: ") }
                append(item.voteAverage.toString())
            }
            setIsFavorite(item.isFavorite)
        }

        fun setIsFavorite(isFavorite: Boolean) {
            val favIcon = if (isFavorite) R.drawable.ic_favorite_fill
            else R.drawable.ic_favorite_outline
            binding.imgFavorite.setImageResource(favIcon)
        }

    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

}
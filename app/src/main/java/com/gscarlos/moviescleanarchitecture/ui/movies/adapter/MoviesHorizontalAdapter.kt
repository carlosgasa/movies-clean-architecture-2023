package com.gscarlos.moviescleanarchitecture.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.common.Constants
import com.gscarlos.moviescleanarchitecture.common.utils.ImageUtils
import com.gscarlos.moviescleanarchitecture.databinding.ItemHorizontalMovieBinding
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow


class MoviesHorizontalAdapter(private val listener: (MoviesItemEvent) -> Unit) :
    ListAdapter<MovieToShow, MoviesHorizontalAdapter.MoviesHorizontalViewHolder>(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHorizontalViewHolder {
        return MoviesHorizontalViewHolder(
            ItemHorizontalMovieBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_horizontal_movie, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesHorizontalViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie) { listener(MoviesItemEvent.OnFavorite(movie))  }
        holder.itemView.setOnClickListener { listener(MoviesItemEvent.OnItem(movie)) }
    }

    override fun onBindViewHolder(
        holder: MoviesHorizontalViewHolder,
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

    class MoviesHorizontalViewHolder(private val binding: ItemHorizontalMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieToShow, onFavorite :(MovieToShow) -> Unit) = with(binding) {
            ImageUtils.load(imgCover, "${Constants.BASE_MOVIE_IMAGE_URL}w185/${item.posterPath}")
            txtTitle.text = buildSpannedString {
                bold { append(item.title) }
            }
            txtVoteAverage.text = buildSpannedString {
                append(item.voteAverage.toString())
            }
            imgFavorite.setOnClickListener { onFavorite(item) }
            setIsFavorite(item.isFavorite)
        }

        fun setIsFavorite(isFavorite: Boolean) {
            val favIcon = if (isFavorite) R.drawable.ic_favorite_fill
            else R.drawable.ic_favorite_outline
            binding.imgFavorite.setImageResource(favIcon)
        }

    }
}
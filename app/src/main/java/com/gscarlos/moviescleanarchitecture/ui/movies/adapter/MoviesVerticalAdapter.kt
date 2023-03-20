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
import com.gscarlos.moviescleanarchitecture.databinding.ItemVerticalMovieBinding
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow

class MoviesVerticalAdapter(private val listener: (MoviesItemEvent) -> Unit) :
    ListAdapter<MovieToShow, MoviesVerticalAdapter.MovieVerticalViewHolder>(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVerticalViewHolder {
        return MovieVerticalViewHolder(
            ItemVerticalMovieBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_vertical_movie, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: MovieVerticalViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie) { listener(MoviesItemEvent.OnFavorite(movie)) }
        holder.itemView.setOnClickListener { listener(MoviesItemEvent.OnItem(movie)) }
    }

    override fun onBindViewHolder(
        holder: MovieVerticalViewHolder,
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

    class MovieVerticalViewHolder(private val binding: ItemVerticalMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieToShow, onFavorite: (MovieToShow) -> Unit) = with(binding) {
            ImageUtils.load(imgCover, "${Constants.BASE_MOVIE_IMAGE_URL}w185/${item.posterPath}")
            txtTitle.text = buildSpannedString {
                bold { append("${itemView.context.getString(R.string.lbl_title)}: ") }
                append(item.title)
            }
            txtVoteAverage.text = buildSpannedString {
                bold { append("${itemView.context.getString(R.string.lbl_vote_average)}: ") }
                append(item.voteAverage.toString())
            }
            txtOverview.text = item.overview
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
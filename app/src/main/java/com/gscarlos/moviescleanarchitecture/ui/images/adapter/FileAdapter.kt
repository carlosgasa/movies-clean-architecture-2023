package com.gscarlos.moviescleanarchitecture.ui.images.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.common.utils.ImageUtils
import com.gscarlos.moviescleanarchitecture.databinding.ItemFileBinding
import com.gscarlos.moviescleanarchitecture.domain.model.FileToShow

class FileAdapter (private var files: List<FileToShow> = emptyList()) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_file, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(files[position])
    }

    override fun getItemCount(): Int {
        return files.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<FileToShow>) {
        files = list
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemFileBinding.bind(view)

        fun bind(fileModel: FileToShow) {
            ImageUtils.load(binding.ivFile, fileModel.url)
        }

    }



}
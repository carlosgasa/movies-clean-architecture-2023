package com.gscarlos.moviescleanarchitecture.ui.images

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.common.utils.InternetUtils
import com.gscarlos.moviescleanarchitecture.databinding.FragmentImagesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesFragment : Fragment() {


    private val viewModel: ImagesViewModel by viewModels()
    private lateinit var mAdapter: FileAdapter


    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initUiState()
    }

    private fun initUiState() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.filesState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when (it) {
                    ImagesViewState.Error -> {
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.txt_error_uploading),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    ImagesViewState.Loading -> {}
                    ImagesViewState.Start -> {}
                    is ImagesViewState.Success -> {
                        mAdapter.setList(it.files)
                        if (it.files.isNotEmpty()) {
                            binding.tvEmptyData.visibility = View.GONE
                        } else {
                            binding.tvEmptyData.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                binding.pbLoading.visibility = if(it) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private fun initComponents() {
        binding.fabSelect.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncherGallery.launch(intent)
        }

        //Configurar recycler
        mAdapter = FileAdapter()
        binding.rvFiles.setHasFixedSize(true)
        binding.rvFiles.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.rvFiles.adapter = mAdapter
    }


    private val resultLauncherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.let { uri ->
                    if(InternetUtils.isNetworkAvailable(binding.root.context)) {
                        viewModel.uploadFile(uri)
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.txt_error_uploading),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
}
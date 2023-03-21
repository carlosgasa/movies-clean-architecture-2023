package com.gscarlos.moviescleanarchitecture.ui.images

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.common.utils.ImageUtils
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
            if(!checkPermission()) {
                MaterialAlertDialogBuilder(binding.root.context)
                    .setTitle(getString(R.string.txt_attention))
                    .setMessage(getString(R.string.txt_request_to_use))
                    .setPositiveButton(getString(R.string.txt_understand)) { _, _ ->
                        requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA))
                    }
                    .setNegativeButton(getString(R.string.txt_cancel)) { _, _ ->
                        Toast.makeText(binding.root.context, R.string.txt_no_permission, Toast.LENGTH_SHORT).show()
                    }
                    .show()
            } else {
                takePhoto()
            }

        }

        //Configurar recycler
        mAdapter = FileAdapter()
        binding.rvFiles.setHasFixedSize(true)
        binding.rvFiles.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.rvFiles.adapter = mAdapter
    }

    private fun takePhoto() {
        resultLauncherCamera.launch(null)
    }


    private val resultLauncherCamera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview() ) {
            it?.let {
                ImageUtils.getImageUri(binding.root.context, it)?.let {  uri ->
                    viewModel.uploadFile(uri)

                }
            }
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

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(binding.root.context, Manifest.permission.ACCESS_FINE_LOCATION)
        val result1 = ContextCompat.checkSelfPermission(binding.root.context, Manifest.permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var isGranted = true
        permissions.entries.forEach {
            isGranted = isGranted && it.value
        }
        if (isGranted) {
            takePhoto()
        } else {
            Toast.makeText(binding.root.context, R.string.txt_no_permission, Toast.LENGTH_SHORT).show()
        }
    }
}
package com.example.mobilecinemalab.ui.profile.photo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.DialogFragmentProfilePictureChoiceBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileAvatarChoiceDialog : DialogFragment() {

    private lateinit var binding: DialogFragmentProfilePictureChoiceBinding
    private lateinit var viewModel: ProfileAvatarDialogViewModel

    private lateinit var imageUri: Uri
    private var newImageLoaded = false

    interface IReloadListener {
        fun reload()
    }

    private var reloadListener: IReloadListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentProfilePictureChoiceBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[ProfileAvatarDialogViewModel::class.java]

        arguments?.getString("avatar").let {
            if (it != null){
                Glide.with(requireContext()).load(it).into(binding.imageViewAvatarChoice)
            }
        }

        val file = createImageFile()
        imageUri = FileProvider.getUriForFile(
            requireActivity(),
            "com.example.mobilecinemalab.fileprovider",
            file
        )


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.profileDialogSaveBnt.setOnClickListener {
            if (newImageLoaded)
                viewModel.sendAvatarImage(
                    getResizedBitmap(
                        getCapturedImage(
                            imageUri
                        )
                    )
                )
            else
                dialog?.dismiss()
        }

        binding.cameraButton.setOnClickListener {
            when (ContextCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION)) {
                PackageManager.PERMISSION_GRANTED -> {
                    val file = createImageFile()
                    imageUri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.example.mobilecinemalab.fileprovider",
                        file
                    )
                    getCameraImageActivityResultLauncher.launch(imageUri)
                }
                else -> requestPermissionLauncher.launch(CAMERA_PERMISSION)
            }
        }

        binding.galleryButton.setOnClickListener {
            getGalleryImageActivityResultLauncher.launch("image/*")
        }


        viewModel.getSendingAvatarLiveData().observe(viewLifecycleOwner){
            when (it){
                is ApiResponse.Loading -> {
                    binding.progressBarProfileDialog.visibility = View.VISIBLE
                }
                is ApiResponse.Failure -> {
                    binding.progressBarProfileDialog.visibility = View.INVISIBLE

                    val errorDialog = ErrorDialogFragment(getString(R.string.error_send_avatar))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    binding.progressBarProfileDialog.visibility = View.INVISIBLE
                    reloadListener?.reload()
                    dialog?.dismiss()
                }
            }
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            reloadListener = parentFragment as IReloadListener?
        } catch (e: ClassCastException) {
            Log.e("Context not found in Avatar Dialog Fragment", e.message.toString())
        }
    }

    private val getCameraImageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                newImageLoaded = true
                binding.imageViewAvatarChoice.setImageBitmap(getCapturedImage(imageUri))
            }
        }

    private val getGalleryImageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                newImageLoaded = true
                binding.imageViewAvatarChoice.setImageBitmap(
                    getResizedBitmap(
                        getCapturedImage(
                            imageUri
                        )
                    )
                )
            }
        }

    private fun getCapturedImage(selectedImage: Uri): Bitmap {
        return when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                requireActivity().contentResolver,
                selectedImage
            )
            else -> {
                val source =
                    ImageDecoder.createSource(requireActivity().contentResolver, selectedImage)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    private fun getResizedBitmap(bm: Bitmap): Bitmap? {
        val maxHeight = 1000
        val maxWidth = 1000
        val scale: Float =
            (maxHeight.toFloat() / bm.width).coerceAtMost(maxWidth.toFloat() / bm.height)

        val matrix = Matrix()
        matrix.postScale(scale, scale)
        return Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getCameraImageActivityResultLauncher.launch(imageUri)
            } else {
                val errorDialog = ErrorDialogFragment(getString(R.string.error_get_permissions))
                errorDialog.show(requireActivity().supportFragmentManager, "Problems")
            }
        }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }

    companion object {
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}
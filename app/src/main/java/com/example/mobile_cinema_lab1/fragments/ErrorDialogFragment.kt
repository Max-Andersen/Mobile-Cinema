package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mobile_cinema_lab1.databinding.ErrorDialogBinding

class ErrorDialogFragment(private val errorMessage: String? = null) : DialogFragment() {

    private lateinit var binding: ErrorDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ErrorDialogBinding.inflate(inflater, container, false)

        if (errorMessage != null) binding.errorText.text = errorMessage

        return binding.root
    }

}
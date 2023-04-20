package com.example.mobilecinemalab.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.databinding.AllCollectionIconsBinding

class SelectCollectionIconFragment: Fragment() {
    private lateinit var binding: AllCollectionIconsBinding

    companion object{
        const val ICON_RESULT_KEY = "Selected_icon_from_all"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AllCollectionIconsBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).hideBottomNavigation()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.icon1.setOnClickListener {
            iconSelected("1")
        }

        binding.icon2.setOnClickListener {
            iconSelected("2")
        }

        binding.icon3.setOnClickListener {
            iconSelected("3")
        }

        binding.icon4.setOnClickListener {
            iconSelected("4")
        }

        binding.icon5.setOnClickListener {
            iconSelected("5")
        }

        binding.icon6.setOnClickListener {
            iconSelected("6")
        }

        binding.icon7.setOnClickListener {
            iconSelected("7")
        }

        binding.icon8.setOnClickListener {
            iconSelected("8")
        }

        binding.icon9.setOnClickListener {
            iconSelected("9")
        }

        binding.icon10.setOnClickListener {
            iconSelected("10")
        }

        binding.icon11.setOnClickListener {
            iconSelected("11")
        }

        binding.icon12.setOnClickListener {
            iconSelected("12")
        }

        binding.icon13.setOnClickListener {
            iconSelected("13")
        }

        binding.icon14.setOnClickListener {
            iconSelected("14")
        }

        binding.icon15.setOnClickListener {
            iconSelected("15")
        }

        binding.icon16.setOnClickListener {
            iconSelected("16")
        }

        binding.icon17.setOnClickListener {
            iconSelected("17")
        }

        binding.icon18.setOnClickListener {
            iconSelected("18")
        }

        binding.icon19.setOnClickListener {
            iconSelected("19")
        }

        binding.icon20.setOnClickListener {
            iconSelected("20")
        }

        binding.icon21.setOnClickListener {
            iconSelected("21")
        }

        binding.icon22.setOnClickListener {
            iconSelected("22")
        }

        binding.icon23.setOnClickListener {
            iconSelected("23")
        }

        binding.icon24.setOnClickListener {
            iconSelected("24")
        }

        binding.icon25.setOnClickListener {
            iconSelected("25")
        }

        binding.icon26.setOnClickListener {
            iconSelected("26")
        }

        binding.icon27.setOnClickListener {
            iconSelected("27")
        }

        binding.icon28.setOnClickListener {
            iconSelected("28")
        }

        binding.icon29.setOnClickListener {
            iconSelected("29")
        }

        binding.icon30.setOnClickListener {
            iconSelected("30")
        }

        binding.icon31.setOnClickListener {
            iconSelected("31")
        }

        binding.icon32.setOnClickListener {
            iconSelected("32")
        }

        binding.icon33.setOnClickListener {
            iconSelected("33")
        }

        binding.icon34.setOnClickListener {
            iconSelected("34")
        }

        binding.icon35.setOnClickListener {
            iconSelected("35")
        }

        binding.icon36.setOnClickListener {
            iconSelected("36")
        }


        return binding.root
    }

    private fun iconSelected(iconId: String){
        setFragmentResult(ICON_RESULT_KEY, bundleOf("icon" to iconId))
        findNavController().navigateUp()
    }

}
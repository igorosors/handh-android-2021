package com.example.lesson_7_strelyukhin.presentation

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.lesson_7_strelyukhin.R
import com.example.lesson_7_strelyukhin.data.model.Bridge
import com.example.lesson_7_strelyukhin.databinding.FragmentInformationBinding
import com.google.android.material.appbar.AppBarLayout


class FragmentInformation : Fragment(R.layout.fragment_information) {
    companion object {
        const val EXTRA_BRIDGE = "extra_bridge"

        fun newInstance(bridge: Bridge): FragmentInformation {
            return FragmentInformation().apply {
                arguments = bundleOf(
                    EXTRA_BRIDGE to bridge
                )
            }
        }
    }

    private var fragmentListener: FragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            fragmentListener = context
        }
    }

    override fun onDetach() {
        fragmentListener = null
        super.onDetach()
    }

    private val binding by viewBinding(FragmentInformationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bridge = arguments?.getParcelable<Bridge>(EXTRA_BRIDGE)

        binding.buttonNotification.setOnClickListener {
            showAlert("${bridge?.name.toString()} мост")
        }

        binding.appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1
            var isShow = true
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.toolbarLayout.title = bridge?.name
                    isShow = true
                } else if (isShow) {
                    binding.toolbarLayout.title = " "
                    isShow = false
                }
            }

        })

        bridge?.getImageId()?.let { binding.imageViewBridgeState.setImageResource(it) }
        binding.textViewBridgeName.text = bridge?.name
        if (bridge != null) {
            binding.textViewBridgeTime.text = buildString {
                bridge.divorces?.forEach { divorce ->
                    append("${divorce.start} — ${divorce.end}\t\t\t")
                }
            }
        }

        binding.textViewBridgeInfo.text = bridge?.info

        binding.imageViewToolbarBackground.setImageResource(R.drawable.ic_launcher_background)

        binding.toolbar.setNavigationOnClickListener {
            fragmentListener?.back("key_information")
        }
        Glide.with(this)
            .asBitmap()
            .load(if (bridge?.getImageId() == R.drawable.ic_bridge_late) bridge.photoCloseUrl else bridge?.photoOpenUrl)
            .into(binding.imageViewToolbarBackground)
    }

    private fun showAlert(title: String) {
        val inflateView = layoutInflater.inflate(R.layout.number_picker, null)
        val numbers = arrayOf("15 мин", "30 мин", "45 мин", "60 мин")
        val numberPicker = inflateView.findViewById<NumberPicker>(R.id.numberPicker).apply {
            minValue = 0
            maxValue = numbers.size - 1
            displayedValues = numbers
            wrapSelectorWheel = false
        }

        numberPicker.setOnValueChangedListener { _, _, newVal ->
            Log.d("lll", newVal.toString())
        }
        val alertDialog =
            AlertDialog.Builder(object : ContextThemeWrapper(requireContext(), R.style.AlertDialogTheme) {})
                .apply {
                    val customTitle = layoutInflater.inflate(R.layout.custom_title, null)
                    (customTitle as TextView).text = title
                    setCustomTitle(customTitle)
                    setView(inflateView)
                    setMessage("За сколько до закрытия моста вас предупредить?")
                    setNegativeButton("ОТМЕНИТЬ") { _, _ -> }
                    setPositiveButton("ОК") { _, _ ->
                        createNotification()
                    }
                    create()
                }
        alertDialog.show()
    }

    private fun createNotification() {

    }
}
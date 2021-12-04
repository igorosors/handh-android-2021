package com.example.lesson_7_strelyukhin.presentation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lesson_7_strelyukhin.R
import com.example.lesson_7_strelyukhin.data.model.AdapterElement
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout


class FragmentInformation : Fragment(R.layout.fragment_information) {
    companion object {
        const val EXTRA_BRIDGE = "extra_bridge"
        var FRAGMENT_STATE = false

        fun newInstance(bridge: AdapterElement): FragmentInformation {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FRAGMENT_STATE = true

        val appBarLayout = view.findViewById<AppBarLayout>(R.id.appBarLayout)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val bridge = arguments?.getParcelable<AdapterElement>(EXTRA_BRIDGE)
        val imageViewToolbarBackground = view.findViewById<ImageView>(R.id.imageViewToolbarBackground)
        val toolbarLayout = view.findViewById<CollapsingToolbarLayout>(R.id.toolbarLayout)

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1
            var isShow = true
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbarLayout.title = bridge?.name
                    isShow = true
                } else if (isShow) {
                    toolbarLayout.title = " "
                    isShow = false
                }
            }

        })

        val buttonNotification = view.findViewById<FrameLayout>(R.id.buttonNotification)
        val imageViewBridgeState = view.findViewById<ImageView>(R.id.imageViewBridgeState)
        val textViewBridgeName = view.findViewById<TextView>(R.id.textViewBridgeName)
        val textViewBridgeTime = view.findViewById<TextView>(R.id.textViewBridgeTime)
        val textViewBridgeInfo = view.findViewById<TextView>(R.id.textViewBridgeInfo)

        buttonNotification.setOnClickListener {

        }

        bridge?.getImageId()?.let { imageViewBridgeState.setImageResource(it) }
        textViewBridgeName.text = bridge?.name
        if (bridge != null) {
            textViewBridgeTime.text = buildString {
                bridge.divorces?.forEach { divorce ->
                    append("${divorce.start} — ${divorce.end}\t\t\t")
                }
            }
        }

        textViewBridgeInfo.text = buildString {
            for (i in 1..8) append("${bridge?.info}\n\n")
        }

        imageViewToolbarBackground.setImageResource(R.drawable.ic_launcher_background)

        toolbar?.setNavigationOnClickListener {
            fragmentListener?.back("key_information")
        }
        Glide.with(this)
            .asBitmap()
            .load(if (bridge?.getImageId() == R.drawable.ic_bridge_late) bridge.photoCloseUrl else bridge?.photoOpenUrl)
            .into(imageViewToolbarBackground)
    }

    override fun onDestroyView() {
        FRAGMENT_STATE = false
        super.onDestroyView()
    }
}
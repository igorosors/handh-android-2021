package com.example.lesson_6_strelyukhin.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.lesson_6_strelyukhin.FragmentListener
import com.example.lesson_6_strelyukhin.R

class PagerFragment : Fragment(R.layout.pager_fragment) {
    companion object {
        const val EXTRA_IMAGE_ID = "extra_image_id"
        const val EXTRA_POSITION = "extra_position"

        fun newInstance(id: Int, position: Int): PagerFragment {
            return PagerFragment().apply {
                arguments = bundleOf(
                    EXTRA_IMAGE_ID to id,
                    EXTRA_POSITION to position + 1
                )
            }
        }
    }

    private var fragmentListener: FragmentListener? = null
    private val imageId by lazy { arguments?.getInt(EXTRA_IMAGE_ID) }
    private val position by lazy { arguments?.getInt(EXTRA_POSITION) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)


        imageView.setImageResource(imageId as Int)
        imageView.setOnClickListener {
            fragmentListener?.toast(context, textViewTitle.text.toString())
        }
        textViewTitle.text = "Картинка $position"

    }

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

}

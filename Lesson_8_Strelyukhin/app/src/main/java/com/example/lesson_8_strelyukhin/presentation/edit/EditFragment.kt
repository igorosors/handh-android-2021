package com.example.lesson_8_strelyukhin.presentation.edit

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lesson_8_strelyukhin.R
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity
import com.example.lesson_8_strelyukhin.presentation.FragmentListener
import com.example.lesson_8_strelyukhin.presentation.list.NotesFragment

class EditFragment : Fragment(R.layout.fragment_edit) {
    companion object {
        private const val NOTE = "note"
        fun newInstance(note: NoteEntity): EditFragment {
            return EditFragment().apply {
                arguments = bundleOf(
                    NOTE to note
                )
            }
        }
        fun newInstance(): EditFragment {
            return EditFragment()
        }
    }

    private var fragmentListener: FragmentListener? = null
    private val viewModel: EditViewModel by viewModels()

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
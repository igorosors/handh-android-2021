package com.example.lesson_8_strelyukhin.presentation.edit

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_8_strelyukhin.R
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity
import com.example.lesson_8_strelyukhin.databinding.FragmentEditBinding
import com.example.lesson_8_strelyukhin.presentation.FragmentListener
import com.example.lesson_8_strelyukhin.presentation.color_selector.ButtonsAdapter
import com.example.lesson_8_strelyukhin.presentation.MyDividerItemDecoration

class EditFragment : Fragment(R.layout.fragment_edit) {
    companion object {
        private const val EXTRA_NOTE = "extra_note"
        fun newInstance(note: NoteEntity): EditFragment {
            return EditFragment().apply {
                arguments = bundleOf(
                    EXTRA_NOTE to note
                )
            }
        }

    }

    private var fragmentListener: FragmentListener? = null
    private val viewModel: EditViewModel by viewModels()
    private val binding by viewBinding(FragmentEditBinding::bind)


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

    private fun setColor() {
        val noteNullable = arguments?.getParcelable<NoteEntity>(EXTRA_NOTE)
        val note = noteNullable ?: NoteEntity(title = "", text = "")
        binding.layoutNote.setBackgroundColor(ContextCompat.getColor(requireContext(),note.color))
        binding.editTextNoteTitle.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (note.color == R.color.white) R.color.black else R.color.white
            )
        )
        binding.editTextNoteText.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (note?.color == R.color.white) R.color.gray else R.color.white
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteNullable = arguments?.getParcelable<NoteEntity>(EXTRA_NOTE)
        val note = noteNullable ?: NoteEntity(title = "", text = "")
        binding.editTextNoteTitle.setText(note.title)
        binding.editTextNoteText.setText(note.text)
        setColor()

        binding.toolbar.setNavigationOnClickListener {
            note.title = binding.editTextNoteTitle.text.toString()
            note.text = binding.editTextNoteText.text.toString()
            viewModel.saveNotes(requireContext(), note) { fragmentListener?.back() }
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_color -> {
                    val inflater = layoutInflater
                    val dialogView = inflater.inflate(R.layout.color_selector, null)
                    val adapter = ButtonsAdapter()
                    val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapter
                    adapter.onItemClick = { color ->
                        note.color = color
                        setColor()
                    }
                    adapter.setItems(
                        listOf(
                            R.color.red,
                            R.color.crimson,
                            R.color.dark_magenta,
                            R.color.slate_blue,
                            R.color.royal_blue,
                            R.color.sky_blue,
                            R.color.dark_turquoise,
                            R.color.dark_cyan,
                            R.color.green,
                            R.color.yellow_green,
                            R.color.yellow,
                            R.color.gold,
                            R.color.orange_red,
                            R.color.dark_gray,
                            R.color.slate_gray,
                            R.color.white,
                        )
                    )
                    val dividerItemDecoration = MyDividerItemDecoration(
                        resources.getDimensionPixelSize(R.dimen.divider)
                    )
                    recyclerView.addItemDecoration(dividerItemDecoration)

                    AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme).apply {
                        setTitle("Выбор цвета")
                        setNegativeButton("Отмена") { _, _ -> }
                        setView(dialogView)
                        show()
                    }

                    true
                }
                else -> {
                    false
                }
            }
        }
    }

}
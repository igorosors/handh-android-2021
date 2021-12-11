package com.example.lesson_8_strelyukhin.presentation.list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_8_strelyukhin.R
import com.example.lesson_8_strelyukhin.data.LoadingState
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity
import com.example.lesson_8_strelyukhin.databinding.FragmentNotesBinding
import com.example.lesson_8_strelyukhin.presentation.FragmentListener
import com.example.lesson_8_strelyukhin.presentation.edit.EditFragment
import java.lang.Exception

class NotesFragment : Fragment(R.layout.fragment_notes) {
    companion object {
        private const val STATE_LOADING = 0
        private const val STATE_DATA = 1
        private const val STATE_ERROR = 2

        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }

    private var fragmentListener: FragmentListener? = null
    private val viewModel: NotesViewModel by viewModels()
    private val binding by viewBinding(FragmentNotesBinding::bind)
    private val adapter = NotesAdapter()

    //______________________________ATTACH_DETACH_START______________________________________

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

    //_______________________________ATTACH_DETACH_END________________________________________

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.subscribeToNotes(requireContext())
        viewModel.loadNotes(requireContext())
    }

    //_______________________________________STATES_START_____________________________________

    private fun setStateLoading() {
        binding.viewFlipper.displayedChild = STATE_LOADING
        binding.buttonUpdate.visibility = GONE
    }

    private fun setStateData(notes: List<NoteEntity>, adapter: NotesAdapter) {
        binding.viewFlipper.displayedChild = STATE_DATA
        binding.buttonUpdate.visibility = GONE
        adapter.setItems(notes)
    }

    private fun setStateEmpty() {
        binding.viewFlipper.displayedChild = STATE_ERROR
        binding.textViewError.text = "Похоже, здесь пусто"
        binding.buttonUpdate.visibility = GONE
    }

    private fun setStateError(e: Exception) {
        binding.viewFlipper.displayedChild = STATE_ERROR
        binding.textViewError.text = e.message
        binding.buttonUpdate.visibility = VISIBLE
        binding.buttonUpdate.setOnClickListener {
            viewModel.loadNotes(requireContext())
        }
    }

    //______________________________________STATES_END____________________________________________

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //________________________________RECYCLE_VIEW_START_______________________________

        adapter.onItemClick = {
            fragmentListener?.switchToFragment(EditFragment.newInstance(it))
        }

        adapter.onLongItemClick = { note ->
            AlertDialog.Builder(this.context).apply {
                setTitle("")
                setPositiveButton("Удалить") { _, _ ->
                    viewModel.deleteNote(context, note)
                }
                setNegativeButton("Архивировать") { _, _ ->
                    viewModel.archiveNote(context, note)
                }
                show()
            }
        }

        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = adapter

        //________________________________RECYCLE_VIEW_END_________________________________

        binding.floatingActionButton.setOnClickListener {
            fragmentListener?.switchToFragment(EditFragment.newInstance())
        }

        viewModel.loadingStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState.Loading -> setStateLoading()
                is LoadingState.Data ->
                    viewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->
                        if (notes.isEmpty()) {
                            setStateEmpty()
                        } else {
                            setStateData(notes, adapter)
                        }
                    }
                is LoadingState.Error -> setStateError(state.error)
            }
        }

    }


}
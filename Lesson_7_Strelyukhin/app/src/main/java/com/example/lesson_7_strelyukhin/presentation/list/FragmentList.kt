package com.example.lesson_7_strelyukhin.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lesson_7_strelyukhin.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_7_strelyukhin.data.LoadingState
import com.example.lesson_7_strelyukhin.data.model.Bridge
import com.example.lesson_7_strelyukhin.databinding.FragmentListBinding
import com.example.lesson_7_strelyukhin.presentation.FragmentInformation
import com.example.lesson_7_strelyukhin.presentation.FragmentListener
import java.lang.Exception

class FragmentList : Fragment(R.layout.fragment_list) {

    companion object {
        const val STATE_LOADING = 0
        const val STATE_ERROR = 1
        const val STATE_DATA = 2
        fun newInstance(): FragmentList {
            return FragmentList()
        }
    }

    private val viewModel: FragmentListViewModel by viewModels()
    private val binding by viewBinding(FragmentListBinding::bind)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBridges()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonRefresh.setOnClickListener {
            viewModel.loadBridges()
        }
        viewModel.bridgeListStateLiveData.observe(viewLifecycleOwner, { state ->
            when (state) {
                is LoadingState.Loading -> {
                    setStateLoading()
                }
                is LoadingState.Data -> {
                    if (state.data.isEmpty()) {
                        setStateEmpty()
                    } else {
                        setStateData(state.data)
                    }
                }
                is LoadingState.Error -> {
                    setStateError(state.error)
                }
                else -> setStateLoading()
            }

        })
    }

    private fun setStateLoading() {
        binding.viewFlipper.displayedChild = STATE_LOADING
    }

    private fun setStateData(bridges: List<Bridge>) {
        binding.viewFlipper.displayedChild = STATE_DATA
        val adapter = MainAdapter()
        binding.recyclerView.adapter = adapter
        adapter.setItems(bridges)
        adapter.onItemClick = {
            fragmentListener?.switchToFragment(FragmentInformation.newInstance(it))
        }
        adapter.onBellClick = {
            viewModel.changeBellState(it)
        }

    }

    private fun setStateEmpty() {
        binding.viewFlipper.displayedChild = STATE_ERROR
        binding.textViewError.text = "Не удалось получить данные, попробуйте ещё раз"
    }

    private fun setStateError(e: Exception) {
        binding.viewFlipper.displayedChild = STATE_ERROR
        binding.textViewError.text = e.message
    }

}






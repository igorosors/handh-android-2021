package com.example.lesson_7_strelyukhin.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_7_strelyukhin.R
import com.example.lesson_7_strelyukhin.data.model.AdapterElement
import com.example.lesson_7_strelyukhin.data.remote.BridgesApi
import com.example.lesson_7_strelyukhin.presentation.FragmentInformation
import com.example.lesson_7_strelyukhin.presentation.FragmentListener
import com.google.android.material.button.MaterialButton
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

    private var fragmentListener: FragmentListener? = null
    private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.recyclerView) }
    private val viewFlipper by lazy { view?.findViewById<ViewFlipper>(R.id.viewFlipper) }
    private val textViewError by lazy { view?.findViewById<TextView>(R.id.textViewError) }
    private val buttonRefresh by lazy { view?.findViewById<MaterialButton>(R.id.buttonRefresh) }

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

    private fun loadBridges() {
        lifecycleScope.launchWhenStarted {
            try {
                setStateLoading()
                val bridges = BridgesApi.apiService.getBridges()
                if (bridges.isEmpty()) {
                    setStateEmpty()
                } else {
                    setStateData(bridges)
                }
            } catch (e: Exception) {
                setStateError(e)
            }
        }
    }

    private fun setStateLoading() {
        viewFlipper?.displayedChild = STATE_LOADING
    }

    private fun setStateData(bridges: List<AdapterElement>) {
        viewFlipper?.displayedChild = STATE_DATA
        val adapter = MainAdapter()
        recyclerView?.adapter = adapter
        adapter.setItems(bridges)
        adapter.onItemClick = {
            fragmentListener?.switchToFragment(FragmentInformation.newInstance(it))
        }
    }

    private fun setStateEmpty() {
        viewFlipper?.displayedChild = STATE_ERROR
        textViewError?.text = "Не удалось получить данные, попробуйте ещё раз"
    }

    private fun setStateError(e: Exception) {
        viewFlipper?.displayedChild = STATE_ERROR
        textViewError?.text = e.message
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBridges()
        buttonRefresh?.setOnClickListener{
            loadBridges()
        }
    }

}






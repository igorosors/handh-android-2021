package com.example.lesson_4_strelyukhin

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_4_strelyukhin.model.Base
import com.example.lesson_4_strelyukhin.model.Detail

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object {
        private const val DETAIL_ITEM = 0
        private const val ROW_DETAIL_ITEM = 1
        private const val BASE_ITEM = 2
    }

    private val items = mutableListOf<AdapterElement>()

    // Определю позже, иначе бросит ошибку
    lateinit var onItemClick: (String) -> Unit

    //parent - контейнер, куда добавится вью, вью тайп всегда инт
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            DETAIL_ITEM -> CellViewHolder(parent, onItemClick)
            ROW_DETAIL_ITEM -> RowViewHolder(parent, onItemClick)
            BASE_ITEM -> RowViewHolder(parent, onItemClick)
            else -> throw Exception("Unsupported view type")
        }
    }

    //сетит на вьюхолдер данные
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            DETAIL_ITEM -> (holder as CellViewHolder).bind(items[position] as Detail)
            ROW_DETAIL_ITEM -> (holder as RowViewHolder).bind(items[position] as Detail)
            BASE_ITEM -> (holder as RowViewHolder).bind(items[position] as Base)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<AdapterElement>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        //оповещает адаптер, что нужно перерисовать список, потому что элементы изменились, иначе адаптер не перерисует
        notifyDataSetChanged()
    }


    fun getSpanSize(position: Int): Int {
        return if ((getItemViewType(position) == BASE_ITEM) ||
            (getItemViewType(position) == ROW_DETAIL_ITEM)) {
            2
        } else {
            1
        }
    }

    //определяет, к какому типу относится макет
    override fun getItemViewType(position: Int): Int {
        return when {
            (items.getOrNull(position + 1) is Detail) ||
                    (items[position] is Detail) &&
                    (position % 2 != 0) -> DETAIL_ITEM
            (items[position] is Detail) &&
                    (position % 2 == 0) -> ROW_DETAIL_ITEM
            else -> BASE_ITEM
        }
    }
}

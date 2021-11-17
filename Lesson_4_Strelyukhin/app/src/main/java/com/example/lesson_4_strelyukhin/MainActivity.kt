package com.example.lesson_4_strelyukhin

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_4_strelyukhin.model.Base
import com.example.lesson_4_strelyukhin.model.Detail
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    companion object {
        private const val VIEW_STATE_LOADING = 0
        private const val VIEW_STATE_DATA = 1
        private const val VIEW_STATE_ERROR = 2
    }

    private val viewFlipper by lazy { findViewById<ViewFlipper>(R.id.viewFlipper) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar)}

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show()
        }
        toolbar.menu.findItem(R.id.menu).setOnMenuItemClickListener {
            Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show()
            true
        }
        toolbar.menu.findItem(R.id.info).setOnMenuItemClickListener {
            val myAlertDialogBuilder = AlertDialog.Builder(this@MainActivity)
            myAlertDialogBuilder.setTitle("Information")
            myAlertDialogBuilder.setMessage("This is some information")
            myAlertDialogBuilder.setPositiveButton("OK") {_, _ ->}
            val mAlertDialog = myAlertDialogBuilder.create()
            mAlertDialog.show()
            true
        }

        val coordinatorLayout = findViewById<View>(R.id.coordinator)
        val adapter = MainAdapter()
        recyclerView.adapter = adapter
        viewFlipper.displayedChild = VIEW_STATE_DATA

        adapter.onItemClick = { userName ->
            val snackbar: Snackbar = Snackbar.make(coordinatorLayout, userName, Snackbar.LENGTH_SHORT)
            snackbar.show()
        }

        val gridLayoutManager = GridLayoutManager (this, 2)
        recyclerView.layoutManager = gridLayoutManager

        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getSpanSize(position)
            }
        }

        val dividerItemDecoration = MyDividerItemDecoration(4.toPx())
        recyclerView.addItemDecoration(dividerItemDecoration)

        adapter.setItems(
            listOf(
                *listOf(
                    Detail(title = "Квитанции", content = "-40080,55", icon = R.drawable.ic_kvit, true),
                    Detail(title = "Счетчики", content = "Подайте показания", icon = R.drawable.ic_counter, true),
                    Detail(title = "Рассрочка", content = "Сл. платеж 25.02.2018", icon = R.drawable.ic_hire),
                    Detail(title = "Страхование", content = "Полис до 12.01.2019", icon = R.drawable.ic_insure),
                    Detail(title = "Интернет и ТВ", content = "Баланс 350 ₽", icon = R.drawable.ic_internet),
                    Detail(title = "Домофон", content = "Подключен", icon = R.drawable.ic_doorphone),
                    Detail(title = "Охрана", content = "Нет", icon = R.drawable.ic_security),
                ).toTypedArray(),
                *listOf(
                    Base(title = "Контакты УК и служб", icon = R.drawable.ic_contacts),
                    Base(title = "Мои заявки", icon = R.drawable.ic_requests),
                    Base(title = "Памятка жителя А101", icon = R.drawable.ic_memo),
                ).toTypedArray(),
            )
        )
    }
}

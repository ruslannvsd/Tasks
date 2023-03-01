package com.example.tasks.popups

import android.content.Context
import android.view.ContextThemeWrapper
import android.widget.PopupMenu
import androidx.cardview.widget.CardView
import com.example.tasks.R

object Menu {
    fun menu(
        ctx: Context,
        view: CardView,
        add: () -> Unit,
        rename: () -> Unit,
        delete: () -> Unit,
        addName: String
    ) {
        val popupMenu = PopupMenu(ctx, view)
        popupMenu.menuInflater.inflate(R.menu.menu_layout, popupMenu.menu)
        popupMenu.menu.getItem(0).title = addName
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.add -> {
                    add()
                    true
                }
                R.id.rename -> {
                    rename()
                    true
                }
                else -> {
                    delete()
                    true}
            }
        }
        popupMenu.show()
    }
}
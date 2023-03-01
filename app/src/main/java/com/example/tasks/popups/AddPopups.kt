package com.example.tasks.popups

import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.widget.PopupWindowCompat
import com.example.tasks.R
import com.example.tasks.data.area.Area
import com.example.tasks.data.area.AreaVM
import com.example.tasks.data.thread.Thd
import com.example.tasks.data.thread.ThdVM
import com.example.tasks.databinding.AddLayoutBinding

object AddPopups {
    fun addAreaPopup(ctx: Context, vm: AreaVM, passedArea: Area?, threadsIntoRv: (Long) -> Unit) {
        val inflater =
            ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView =
            inflater.inflate(R.layout.add_layout, LinearLayout(ctx), false)
        val bnd = AddLayoutBinding.bind(popupView)
        val window = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        window.elevation = 2f
        window.showAtLocation(popupView, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER)
        PopupWindowCompat.setWindowLayoutType(
            window,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        val enter = bnd.entered
        bnd.text.text = "Area"
        if (passedArea == null) {
            enter.hint = "Enter a new area of activity"
        } else enter.setText(passedArea.areaStr)
        enter.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (passedArea != null) {
                    if (enter.text.isNotEmpty()) {
                        val areaName = enter.text.toString()
                        val area = Area(passedArea.id, areaName, passedArea.areaL, passedArea.status)
                        vm.update(area)
                        threadsIntoRv(area.areaL)
                        Toast.makeText(ctx, "${passedArea.areaStr} was renamed", Toast.LENGTH_LONG).show()
                        window.dismiss()
                    }
                } else {
                    val long = System.currentTimeMillis()
                    if (enter.text.isNotEmpty()) {
                        val areaName = enter.text.toString()
                        val area = Area(0, areaName, long, true)
                        vm.insert(area)
                        threadsIntoRv(area.areaL)
                        Toast.makeText(ctx, area.toString(), Toast.LENGTH_LONG).show()
                        window.dismiss()
                        Toast.makeText(ctx, "${area.areaStr} was added", Toast.LENGTH_LONG).show()
                    }
                }
            }
            true
        }
    }

    fun addThdPopup(ctx: Context, area: Long, vm: ThdVM, thread: Thd?) {
        val inflater =
            ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView =
            inflater.inflate(R.layout.add_layout, LinearLayout(ctx), false)
        val bnd = AddLayoutBinding.bind(popupView)
        val window = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        window.elevation = 2f
        window.showAtLocation(popupView, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER)
        PopupWindowCompat.setWindowLayoutType(
            window,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        val enter = bnd.entered
        bnd.text.text = "Thread"
        if (thread == null) {
            enter.hint = "Open a new thread of tasks"
        } else enter.setText(thread.thdStr)

        enter.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (thread == null) {
                    val long = System.currentTimeMillis()
                    if (enter.text.isNotEmpty()) {
                        val thdName = enter.text.toString()
                        val thd =
                            Thd(id = 0, thdStr = thdName, thdL = long, area = area, status = true)
                        vm.insert(thd)
                        window.dismiss()
                    }
                } else {
                    if (enter.text.isNotEmpty()) {
                        val thdName = enter.text.toString()
                        val thd =
                            Thd(thread.id, thdName, thread.thdL, thread.area, thread.status)
                        vm.update(thd)
                        window.dismiss()
                    }
                }
            }
            true
        }
    }
}
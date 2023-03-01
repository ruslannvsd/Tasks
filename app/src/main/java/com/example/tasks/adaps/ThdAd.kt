package com.example.tasks.adaps

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.data.thread.Thd
import com.example.tasks.databinding.RvThreadBinding
import com.example.tasks.popups.Menu

class ThdAd(
    private val ctx: Context,
    private val longs: (Long, Long) -> Unit,
    private val tasks: (Long) -> Unit,
    private val delete: (Thd) -> Unit,
    private val rename: (Long, Thd) -> Unit
) : RecyclerView.Adapter<ThdAd.ThdVH>() {
    private var threads = emptyList<Thd>()
    private var selected = -1
    class ThdVH(val bnd: RvThreadBinding) : RecyclerView.ViewHolder(bnd.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThdVH {
        val bnd = RvThreadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThdVH(bnd)
    }

    override fun onBindViewHolder(h: ThdVH, p: Int) {
        val thd = threads[p]
        val card = h.bnd.card
        h.bnd.thread.text = thd.thdStr
        card.setOnClickListener {
            selected = h.bindingAdapterPosition
            notifyChange()
            tasks(thd.thdL)
        }
        if (selected == p) card.background.setTint(ctx.getColor(R.color.eight))
        else card.background.setTint(ctx.getColor(R.color.dark_blue_three))
        card.setOnLongClickListener {
            val addTask = {
                longs(thd.thdL, thd.area)
            }
            val deleteThd = {
                delete(thd)
            }
            val rename = {
                rename(thd.area, thd)
            }
            Menu.menu(ctx, card, addTask, rename, deleteThd, "Add Task")

            true
        }
    }
    override fun getItemCount() = threads.size
    fun setThd(threads: List<Thd>, selected: Int) {
        this.threads = threads
        this.selected = selected
        notifyChange()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun notifyChange() = notifyDataSetChanged()
}
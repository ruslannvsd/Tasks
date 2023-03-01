package com.example.tasks.adaps

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.data.area.Area
import com.example.tasks.databinding.RvAreaBinding
import com.example.tasks.popups.Menu

class AreaAd(
    private val ctx: Context,
    private val setThd: (Long) -> Unit,
    private val addThd: (Long) -> Unit,
    private val deleteArea: (Area) -> Unit,
    private val rename: (Area) -> Unit
) : RecyclerView.Adapter<AreaAd.AreaVH>() {
    private var areas = emptyList<Area>()
    private var selected = -1

    class AreaVH(
        val bnd: RvAreaBinding
    ) : RecyclerView.ViewHolder(bnd.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaVH {
        val bnd = RvAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AreaVH(bnd)
    }

    override fun getItemCount() = areas.size

    override fun onBindViewHolder(h: AreaVH, p: Int) {
        val ar = areas[p]
        val card = h.bnd.card
        card.setOnClickListener {
            selected = h.bindingAdapterPosition
            notifyChange()
            setThd(ar.areaL)
        }
        if (selected == p) card.background.setTint(ctx.getColor(R.color.eight))
        else card.background.setTint(ctx.getColor(R.color.dark_blue_two))
        h.bnd.area.text = ar.areaStr
        card.setOnLongClickListener {
            if (selected == p) {
                val threadAdd = {
                    addThd(ar.areaL)
                }
                val deleteArea = {
                    deleteArea(ar)
                }
                val renameArea = {
                    rename(ar)
                }
                Menu.menu(ctx, card, threadAdd, renameArea, deleteArea, "Add Thread")
            }
            true
        }
    }

    fun setAreas(areas: List<Area>, selected: Int) {
        this.areas = areas
        this.selected = selected
        notifyChange()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun notifyChange() {
        this.notifyDataSetChanged()
    }
}
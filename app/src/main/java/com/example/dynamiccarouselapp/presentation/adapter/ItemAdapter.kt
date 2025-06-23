package com.example.dynamiccarouselapp.presentation.adapter

import com.example.dynamiccarouselapp.domain.model.ListItemData


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamiccarouselapp.R


class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var itemList: List<ListItemData> = emptyList()
    fun updateList(newList: List<ListItemData>) {
        itemList = newList
        notifyDataSetChanged() // for basic demo; use DiffUtil in real apps
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.itemIcon)
        private val title: TextView = view.findViewById(R.id.itemTitle)
        private val subtitle: TextView = view.findViewById(R.id.itemSubtitle)

        fun bind(item: ListItemData) {
            icon.setImageResource(item.imageResId)
            title.text = item.title
            subtitle.text = item.subtitle
        }
    }
}


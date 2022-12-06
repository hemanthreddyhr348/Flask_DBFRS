package com.binatebits.food_reccomendation_system

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binatebits.food_reccomendation_system.data.entities.MenuCategory
import com.binatebits.food_reccomendation_system.data.entities.TotalItemsResponseItem

import com.binatebits.food_reccomendation_system.databinding.LayoutHeaderBinding

class SectionAdapter(private val context : Context, private val listener: SectionItemListener)  : RecyclerView.Adapter<HeaderViewHolder>() {
    interface SectionItemListener {
        fun onClickedItem(item: MenuCategory , index: Int)
    }
    private var items = ArrayList<MenuCategory>()
    fun setItems(items: ArrayList<MenuCategory>  ) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val binding: LayoutHeaderBinding = LayoutHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder(binding , context ,listener)
    }
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) = holder.bind(items[position])


}

class HeaderViewHolder(private val itemBinding: LayoutHeaderBinding, private val context : Context,private val listener: SectionAdapter.SectionItemListener) : RecyclerView.ViewHolder(itemBinding.root), MainAdapter.SearchItemListener {

    private lateinit var item: MenuCategory



    @SuppressLint("SetTextI18n")
    fun bind(item: MenuCategory) {
        this.item = item


        itemBinding.tvSection.text = item.menuCat
       val adapter = MainAdapter(context , this )
       val linearLayoutManager = LinearLayoutManager(context)
        itemBinding.menuRv.layoutManager = linearLayoutManager
        itemBinding.menuRv.adapter = adapter
        adapter.setItems(item.itemsList)


    }


    override fun onClickedItem(item: TotalItemsResponseItem, index: Int) {
        item.isSelected = !item.isSelected
        this.item.itemsList[index] = item
        listener.onClickedItem(this.item,absoluteAdapterPosition)
    }
}
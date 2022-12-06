package com.binatebits.food_reccomendation_system

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binatebits.food_reccomendation_system.data.entities.TotalItemsResponseItem
import com.binatebits.food_reccomendation_system.databinding.MainItemBinding

class MainAdapter(private val context : Context ,private val  listener:SearchItemListener ):RecyclerView.Adapter<MainViewHolder>() {
    interface SearchItemListener {
        fun onClickedItem(item: TotalItemsResponseItem , index: Int)
    }
    private var items = ArrayList<TotalItemsResponseItem>()
    fun setItems(items: ArrayList<TotalItemsResponseItem> ) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding: MainItemBinding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding , context ,listener)
    }
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = holder.bind(items[position])


}

class MainViewHolder(private val itemBinding: MainItemBinding, private val context : Context ,private val listener: MainAdapter.SearchItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var item: TotalItemsResponseItem

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: TotalItemsResponseItem) {
        this.item = item


        itemBinding.tvMenuName.text = item.menuItems
        itemBinding.tvCalories.text = item.caloriesFromFat
        itemBinding.tvCholestrol.text = item.Cholesterols
        itemBinding.tvDietF.text = item.dietaryFiber
        itemBinding.tvSugar.text = item.totalSugar
        itemBinding.tvCarbohydrats.text = item.totalCarbohydrate
        itemBinding.tvProtein.text = item.protein
        itemBinding.tvTotalF.text = item.totalFatDaily
        if(item.isSelected == true)
        {
            itemBinding.checkButton.isChecked = true
            itemBinding.checkButton.visibility = View.VISIBLE
        }
        else
        {
            itemBinding.checkButton.isChecked = false
            itemBinding.checkButton.visibility = View.VISIBLE
        }
        itemBinding.checkButton.setOnClickListener {
            listener.onClickedItem(item,absoluteAdapterPosition)
        }

    }



    override fun onClick(v: View?) {
        if (v != null) {
            listener.onClickedItem(item,absoluteAdapterPosition)
        }

    }
}

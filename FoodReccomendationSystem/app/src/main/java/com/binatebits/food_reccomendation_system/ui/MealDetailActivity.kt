package com.binatebits.food_reccomendation_system.ui


import androidx.recyclerview.widget.LinearLayoutManager
import com.binatebits.food_reccomendation_system.SectionAdapter
import com.binatebits.food_reccomendation_system.base.BaseActivity
import com.binatebits.food_reccomendation_system.data.entities.MenuCategory
import com.binatebits.food_reccomendation_system.databinding.ActivityMealDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailActivity : BaseActivity(), SectionAdapter.SectionItemListener {
    private lateinit var binding: ActivityMealDetailBinding
    private lateinit var adapter: SectionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun initView() {
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
        setupRecyclerView()
        val menus = intent .getSerializableExtra( "key" )
        adapter.setItems(menus as ArrayList<MenuCategory>)
    }

    private fun setupRecyclerView() {
        adapter = SectionAdapter(this ,this)
        linearLayoutManager = LinearLayoutManager(this)
        binding.menuRv.layoutManager = linearLayoutManager
        binding.menuRv.adapter = adapter
    }

    override fun initListener() {

    }

    override fun onClickedItem(item: MenuCategory, index: Int) {

    }

}
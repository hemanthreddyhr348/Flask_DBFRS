package com.binatebits.food_reccomendation_system


import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binatebits.food_reccomendation_system.application.prefs
import com.binatebits.food_reccomendation_system.base.BaseActivity
import com.binatebits.food_reccomendation_system.data.entities.MenuCategory
import com.binatebits.food_reccomendation_system.data.entities.TotalItemsResponseItem
import com.binatebits.food_reccomendation_system.databinding.ActivityMainBinding
import com.binatebits.food_reccomendation_system.network.Status
import com.binatebits.food_reccomendation_system.ui.MealDetailActivity
import com.binatebits.food_reccomendation_system.util.DisplayBanner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), SectionAdapter.SectionItemListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: SectionAdapter
     private lateinit var linearLayoutManager: LinearLayoutManager
    private val menuCategories = ArrayList<MenuCategory>()
    var calThreshold : Double = 0.0
    var menuCatIndex : Int = 0
    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
        binding.tvUserName.text = prefs.userName
        binding.tvCaloriesCount.text = prefs.calories
        setupRecyclerView()
        setupObservers()
        setupRecItemObserver()
        binding.llProgressBar.layoutProgress.visibility = View.VISIBLE
        viewModel.getTotalItems()
      //  viewModel.getRecommendedItems("Sausage%20McMuffin%20with%20Egg")
        calThreshold = prefs.caloriesCount.toDouble() / 3
        menuCategories.add(MenuCategory("BreakFast",ArrayList()))
    }

    private fun setupRecyclerView() {
        adapter = SectionAdapter(this ,this)
        linearLayoutManager = LinearLayoutManager(this)
        binding.menuRv.layoutManager = linearLayoutManager
        binding.menuRv.adapter = adapter
    }

    override fun initListener() {

        binding.btnSubmit.setOnClickListener {

            if(binding.tvAutocomplete.text.isNotEmpty())
            {
                binding.llProgressBar.layoutProgress.visibility = View.VISIBLE
                viewModel.getRecommendedItems(binding.tvAutocomplete.text.toString())
            }
            else
            {
                DisplayBanner.info(this , resources.getString(R.string.info),"Please input preferred menu items")
            }
        }
    }

    private fun setupObservers() {
        viewModel.responseTotalItems.observe(this) { it1 ->
            when (it1.status) {
                Status.SUCCESS -> {
                    binding.llProgressBar.layoutProgress.visibility = View.GONE
                    val names = it1.data?.map { it.menuItems } as ArrayList<String>
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)
                    binding.tvAutocomplete.setAdapter(adapter)

                }


                Status.ERROR -> {
                    binding.llProgressBar.layoutProgress.visibility = View.GONE
                    it1?.message?.let { it2 ->
                        DisplayBanner.error(
                            this, resources.getString(R.string.error),
                            it2
                        )
                    }
                }

                Status.LOADING -> {
                    binding.llProgressBar.layoutProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupRecItemObserver() {
        viewModel.responseRecommendedItems.observe(this) { it1 ->
            when (it1.status) {
                Status.SUCCESS -> {
                    binding.llProgressBar.layoutProgress.visibility = View.GONE
                       if (it1.data?.isNotEmpty() == true)
                       {
                           checkItemsSelected(it1.data)
//                           menuCategories.clear()
//                           menuCategories.add(MenuCategory("BreakFast",
//                               it1.data.slice(0..1) as ArrayList<TotalItemsResponseItem>
//                           ))
//                           menuCategories.add(MenuCategory("Lunch",it1.data.slice(2..3) as ArrayList<TotalItemsResponseItem>))
//                           menuCategories.add(MenuCategory("Dinner",it1.data.slice(4..5) as ArrayList<TotalItemsResponseItem>))


                       }

                }


                Status.ERROR -> {
                    binding.llProgressBar.layoutProgress.visibility = View.GONE
                    it1?.message?.let { it2 ->
                        DisplayBanner.error(
                            this, resources.getString(R.string.error),
                            it2
                        )
                    }
                }

                Status.LOADING -> {
                    binding.llProgressBar.layoutProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkItemsSelected(item:ArrayList<TotalItemsResponseItem>)
    {
        menuCategories[menuCatIndex].itemsList.removeAll{ !it.isSelected }
        menuCategories[menuCatIndex].itemsList.addAll(item)
        adapter.setItems(menuCategories)
    }

    override fun onClickedItem(item: MenuCategory, index: Int) {

        val filteredItems = menuCategories[index].itemsList.filter { it.isSelected }
        var count = 0.0
        for(value in filteredItems)
        {
            if(value.caloriesFromFat == "NAN")
            {
                count += 80
            }
            else {
                count += value.caloriesFromFat.toDouble()
            }
        }

        if(count < calThreshold)
        {
            menuCategories[index] = item
            adapter.setItems(menuCategories)
        }
        else
        {
            if(menuCatIndex == 0)
            {

                menuCatIndex = 1
                val filteredItem = menuCategories[index].itemsList.filter { !it.isSelected }
                menuCategories.add(MenuCategory("Lunch",
                    filteredItem as ArrayList<TotalItemsResponseItem>
                ))
                adapter.setItems(menuCategories)
                menuCategories[0].itemsList = menuCategories[index].itemsList.filter { it.isSelected } as ArrayList<TotalItemsResponseItem>
            }
            else if (menuCatIndex == 1)
            {

                menuCatIndex = 2
                val filteredItem = menuCategories[index].itemsList.filter { !it.isSelected }
                menuCategories.add(MenuCategory("Dinner",
                    filteredItem as ArrayList<TotalItemsResponseItem>
                ))
                adapter.setItems(menuCategories)
                menuCategories[1].itemsList = menuCategories[index].itemsList.filter { it.isSelected } as ArrayList<TotalItemsResponseItem>
            }
            else
            {
                menuCategories[2].itemsList = menuCategories[index].itemsList.filter { it.isSelected } as ArrayList<TotalItemsResponseItem>
                val intent = Intent(this@MainActivity, MealDetailActivity::class.java)
                intent.putExtra("key", menuCategories)
                startActivity(intent)

            }
        }


    }

}
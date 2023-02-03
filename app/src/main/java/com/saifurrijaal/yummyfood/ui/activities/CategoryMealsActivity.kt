package com.saifurrijaal.yummyfood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.saifurrijaal.yummyfood.R
import com.saifurrijaal.yummyfood.adapter.CategoryMealsAdapter
import com.saifurrijaal.yummyfood.databinding.ActivityCategoryMealsBinding
import com.saifurrijaal.yummyfood.ui.fragments.HomeFragment
import com.saifurrijaal.yummyfood.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMvvm: CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareCategoryItemRecyclerViews()

        categoryMvvm = ViewModelProvider(this).get(CategoryMealsViewModel::class.java)

        categoryMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        observerCategories()

    }

    private fun observerCategories() {
        categoryMvvm.categoryItems.observe(this, Observer {
            categoryMealsAdapter.setMealList(it)
            binding.tvCategoryCount.text = it.size.toString()
        })
    }

    private fun prepareCategoryItemRecyclerViews() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}
package com.saifurrijaal.yummyfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.saifurrijaal.yummyfood.adapter.CategoriesAdapter
import com.saifurrijaal.yummyfood.adapter.MostPopularAdapter
import com.saifurrijaal.yummyfood.data.pojo.MealsByCategory
import com.saifurrijaal.yummyfood.data.pojo.Meal
import com.saifurrijaal.yummyfood.databinding.FragmentHomeBinding
import com.saifurrijaal.yummyfood.ui.activities.CategoryMealsActivity
import com.saifurrijaal.yummyfood.ui.activities.MealActivity
import com.saifurrijaal.yummyfood.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesItemAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.saifurrijaal.yummyfood.ui.fragments.idMeal"
        const val MEAL_NAME = "com.saifurrijaal.yummyfood.ui.fragments.nameMeal"
        const val MEAL_THUMB = "com.saifurrijaal.yummyfood.ui.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.saifurrijaal.yummyfood.ui.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)

        popularItemsAdapter = MostPopularAdapter()
        categoriesItemAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPopularItemRecyclerViews()
        setCategoriesItemRecyclerViews()

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItems()
        onPopularItemClick()

        homeMvvm.getCategories()
        observeCategories()
        onCategoryItemClick()

    }

    private fun onCategoryItemClick() {
        categoriesItemAdapter.onItemClick = {
            startActivity(Intent(activity, CategoryMealsActivity::class.java)
                .putExtra(CATEGORY_NAME, it.strCategory)
            )
        }
    }

    private fun setCategoriesItemRecyclerViews() {
        homeBinding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesItemAdapter
        }
    }

    private fun observeCategories() {
        homeMvvm.categoriesList.observe(viewLifecycleOwner, Observer {
            categoriesItemAdapter.setCategoriesList(it)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {
            startActivity(Intent(activity, MealActivity::class.java)
                .putExtra(MEAL_ID, it.idMeal)
                .putExtra(MEAL_NAME, it.strMeal)
                .putExtra(MEAL_THUMB, it.strMealThumb)
            )
        }
    }

    private fun setPopularItemRecyclerViews() {
        homeBinding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItems() {
        homeMvvm.popularItems.observe(viewLifecycleOwner, {
            popularItemsAdapter.setMeals(it)
        })
    }

    private fun onRandomMealClick() {
        homeBinding.randomMeal.setOnClickListener {
            startActivity(Intent(activity, MealActivity::class.java)
                .putExtra(MEAL_ID, randomMeal.idMeal)
                .putExtra(MEAL_NAME, randomMeal.strMeal)
                .putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            )
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.randomMeal.observe(viewLifecycleOwner, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(homeBinding.imgRandomMeal)

                randomMeal = t
            }
        })
    }
}
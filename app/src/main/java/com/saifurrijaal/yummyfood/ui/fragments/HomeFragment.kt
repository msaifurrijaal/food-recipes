package com.saifurrijaal.yummyfood.ui.fragments

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.saifurrijaal.yummyfood.R
import com.saifurrijaal.yummyfood.adapter.MostPopularAdapter
import com.saifurrijaal.yummyfood.data.pojo.CategoryMeal
import com.saifurrijaal.yummyfood.data.pojo.Meal
import com.saifurrijaal.yummyfood.data.pojo.MealList
import com.saifurrijaal.yummyfood.data.retrofit.RetrofitInstance
import com.saifurrijaal.yummyfood.databinding.FragmentHomeBinding
import com.saifurrijaal.yummyfood.ui.activities.MealActivity
import com.saifurrijaal.yummyfood.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var listPopularMeals : ArrayList<CategoryMeal>

    companion object {
        const val MEAL_ID = "com.saifurrijaal.yummyfood.ui.fragments.idMeal"
        const val MEAL_NAME = "com.saifurrijaal.yummyfood.ui.fragments.nameMeal"
        const val MEAL_THUMB = "com.saifurrijaal.yummyfood.ui.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)

        popularItemsAdapter = MostPopularAdapter()
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

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItems()
        onPopularItemClick()

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
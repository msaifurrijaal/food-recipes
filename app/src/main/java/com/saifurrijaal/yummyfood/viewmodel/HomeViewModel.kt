package com.saifurrijaal.yummyfood.viewmodel

import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.saifurrijaal.yummyfood.R
import com.saifurrijaal.yummyfood.data.pojo.CategoryList
import com.saifurrijaal.yummyfood.data.pojo.CategoryMeal
import com.saifurrijaal.yummyfood.data.pojo.Meal
import com.saifurrijaal.yummyfood.data.pojo.MealList
import com.saifurrijaal.yummyfood.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {

    private val _randomMeal = MutableLiveData<Meal>()
    val randomMeal : LiveData<Meal> = _randomMeal

    private val _popularItems = MutableLiveData<List<CategoryMeal>>()
    val popularItems : LiveData<List<CategoryMeal>> = _popularItems

//    init {
//        getRandomMeal()
//    }

    fun getRandomMeal() {
        RetrofitInstance.getApiService().getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    _randomMeal.value = randomMeal
                } else {
                    Log.e("HomeFragment", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }
        })
    }

    fun getPopularItems() {
        RetrofitInstance.getApiService().getPopularItems("Seafood").enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.isSuccessful) {
                    _popularItems.value = response.body()!!.meals
                } else {
                    Log.e("MainActivity", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("MainActivity", t.message.toString())
            }
        })
    }
}
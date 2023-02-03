package com.saifurrijaal.yummyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saifurrijaal.yummyfood.data.pojo.MealsByCategory
import com.saifurrijaal.yummyfood.data.pojo.MealsByCategoryList
import com.saifurrijaal.yummyfood.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    private val _categoryItems = MutableLiveData<List<MealsByCategory>>()
    val categoryItems : LiveData<List<MealsByCategory>> = _categoryItems

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.getApiService().getMealsByCategory(categoryName).enqueue(object: Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                if (response.isSuccessful) {
                    _categoryItems.value = response.body()!!.meals
                } else {
                    Log.e("MainActivity", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("MainActivity", t.message.toString())
            }

        })
    }
}
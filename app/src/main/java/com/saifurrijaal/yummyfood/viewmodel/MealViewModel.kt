package com.saifurrijaal.yummyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saifurrijaal.yummyfood.data.pojo.Meal
import com.saifurrijaal.yummyfood.data.pojo.MealList
import com.saifurrijaal.yummyfood.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel: ViewModel() {

    private val _mealDetail = MutableLiveData<Meal>()
    val mealDetail : LiveData<Meal> = _mealDetail


    fun getMealDetail(id: String) {
        RetrofitInstance.getApiService().getMealDetails(id).enqueue(object: Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    _mealDetail.value = response.body()!!.meals[0]
                } else {
                    Log.e("ActivityMeal", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("ActivityMeal", t.message.toString())
            }

        })
    }
}
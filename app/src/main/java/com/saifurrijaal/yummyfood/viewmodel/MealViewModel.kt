package com.saifurrijaal.yummyfood.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.saifurrijaal.yummyfood.data.db.MealDatabase
import com.saifurrijaal.yummyfood.data.db.MealRepository
import com.saifurrijaal.yummyfood.data.pojo.Meal
import com.saifurrijaal.yummyfood.data.pojo.MealList
import com.saifurrijaal.yummyfood.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(application: Application): AndroidViewModel(application) {

    private val _mealDetail = MutableLiveData<Meal>()
    val mealDetail : LiveData<Meal> = _mealDetail

    val repository: MealRepository

    init {
        val dao = MealDatabase.getInstance(application).mealDao()
        repository = MealRepository(dao)
    }


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

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            repository.upsertMealRepo(meal)
        }
    }



}
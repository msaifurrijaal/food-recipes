package com.saifurrijaal.yummyfood.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.saifurrijaal.yummyfood.data.db.MealDatabase
import com.saifurrijaal.yummyfood.data.db.MealRepository
import com.saifurrijaal.yummyfood.data.pojo.*
import com.saifurrijaal.yummyfood.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val _randomMeal = MutableLiveData<Meal>()
    val randomMeal : LiveData<Meal> = _randomMeal

    private val _popularItems = MutableLiveData<List<MealsByCategory>>()
    val popularItems : LiveData<List<MealsByCategory>> = _popularItems

    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoriesList : LiveData<List<Category>> = _categoriesList

    val allMeal: LiveData<List<Meal>>
    val repository: MealRepository

    // buat nyimpan state biar ga perlu init
    private var saveStateRandomMeal: Meal? = null

    init {
        val dao = MealDatabase.getInstance(application).mealDao()
        repository = MealRepository(dao)
        allMeal = repository.allMeals

        // Log.d("HomeViewModel", "${allMeal}")
    }

    fun getRandomMeal() {
        saveStateRandomMeal?.let { randomMeal ->
            _randomMeal.postValue(randomMeal)
            return
        }

        RetrofitInstance.getApiService().getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    _randomMeal.value = randomMeal
                    saveStateRandomMeal = randomMeal
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
        RetrofitInstance.getApiService().getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.isSuccessful) {
                    _popularItems.value = response.body()!!.meals
                } else {
                    Log.e("MainActivity", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("MainActivity", t.message.toString())
            }
        })
    }

    fun getCategories() {
        RetrofitInstance.getApiService().getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.isSuccessful) {
                    _categoriesList.value = response.body()!!.categories
                } else {
                    Log.e("MainActivity", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("MainActivity", t.message.toString())
            }

        })
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            repository.deleteMealRepo(meal)
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            repository.upsertMealRepo(meal)
        }
    }
}
package com.saifurrijaal.yummyfood.data.db

import androidx.lifecycle.LiveData
import com.saifurrijaal.yummyfood.data.pojo.Meal

class MealRepository(private val mealDao: MealDao) {

    val allMeals: LiveData<List<Meal>> = mealDao.getAllMeals()

    suspend fun upsertMealRepo(meal: Meal) {
        mealDao.upsertMeal(meal)
    }

    suspend fun deleteMealRepo(meal: Meal) {
        mealDao.deleteMeal(meal)
    }
}
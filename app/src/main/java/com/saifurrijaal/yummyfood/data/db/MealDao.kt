package com.saifurrijaal.yummyfood.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saifurrijaal.yummyfood.data.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals() : LiveData<List<Meal>>
}
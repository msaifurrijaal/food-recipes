package com.saifurrijaal.yummyfood.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saifurrijaal.yummyfood.data.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConventer::class)
abstract class MealDatabase : RoomDatabase(){

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal_db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }
}
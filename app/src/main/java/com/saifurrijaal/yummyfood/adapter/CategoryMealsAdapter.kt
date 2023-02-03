package com.saifurrijaal.yummyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifurrijaal.yummyfood.data.pojo.MealsByCategory
import com.saifurrijaal.yummyfood.databinding.CategoryItemBinding
import com.saifurrijaal.yummyfood.databinding.MealItemBinding

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {

    private var mealList = ArrayList<MealsByCategory>()

    fun setMealList(mealListParam: List<MealsByCategory>) {
        mealList.clear()
        mealList.addAll(mealListParam)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = mealList[position].strMeal
    }

    override fun getItemCount(): Int = mealList.size
}
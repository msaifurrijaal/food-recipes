package com.saifurrijaal.yummyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifurrijaal.yummyfood.data.pojo.CategoryMeal
import com.saifurrijaal.yummyfood.databinding.PopularItemBinding

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.ViewHolder>() {

    lateinit var onItemClick: ((CategoryMeal) -> Unit)
    private var mealList = ArrayList<CategoryMeal>()

    fun setMeals(mealList: List<CategoryMeal>) {
        this.mealList.clear()
        this.mealList.addAll(mealList)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: PopularItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int = mealList.size
}
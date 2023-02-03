package com.saifurrijaal.yummyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifurrijaal.yummyfood.data.pojo.Category
import com.saifurrijaal.yummyfood.databinding.CategoryItemBinding

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null

    fun setCategoriesList(categoriesList: List<Category>) {
        this.categoriesList.clear()
        this.categoriesList.addAll(categoriesList)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int = categoriesList.size
}
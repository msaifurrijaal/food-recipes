package com.saifurrijaal.yummyfood.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.saifurrijaal.yummyfood.R
import com.saifurrijaal.yummyfood.data.db.MealDatabase
import com.saifurrijaal.yummyfood.data.pojo.Meal
import com.saifurrijaal.yummyfood.databinding.ActivityMealBinding
import com.saifurrijaal.yummyfood.ui.fragments.HomeFragment
import com.saifurrijaal.yummyfood.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    val mealMvvm by viewModels<MealViewModel>()
    private lateinit var youtubeLink: String
    private var mealToSave: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // val mealDatabase = MealDatabase.getInstance(context = this)
        // val viewModelFactory = MealViewModelFactory(mealDatabase)
        // mealMvvm = ViewModelProvider(this, viewModelFactory).get(MealViewModel::class.java)

        getMealInformationFromIntent()
        setInformationViews()

        loadingCase()

        mealMvvm.getMealDetail(mealId)
        observerMealDetails()

        onYoutubeClick()

        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.btnAddToFavorite.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Successfully Save!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.imgYoutube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)))
        }
    }

    private fun observerMealDetails() {
        mealMvvm.mealDetail.observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                responseCase()
                val meal = t
                mealToSave = meal

                binding.tvCategoryInfo.text = "Category : ${meal!!.strCategory}"
                binding.tvAreaInfo.text = "Area : ${meal.strArea}"
                binding.tvContent.text = meal.strInstructions

                youtubeLink = meal.strYoutube.toString()
            }
        })
    }

    private fun setInformationViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.btnAddToFavorite.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun responseCase() {
        binding.btnAddToFavorite.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }
}
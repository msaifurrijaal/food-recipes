package com.saifurrijaal.yummyfood.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.saifurrijaal.yummyfood.R
import com.saifurrijaal.yummyfood.adapter.FavoritesMealsAdapter
import com.saifurrijaal.yummyfood.databinding.FragmentFavoritBinding
import com.saifurrijaal.yummyfood.ui.activities.MainActivity
import com.saifurrijaal.yummyfood.viewmodel.HomeViewModel


class FavoritFragment : Fragment() {

    private lateinit var binding: FragmentFavoritBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesMealsAdapter: FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        favoritesMealsAdapter = FavoritesMealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareFavoritesItemRecyclerViews()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesMealsAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_SHORT).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(favoritesMealsAdapter.differ.currentList[position])
                    }
                ).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)

    }

    private fun prepareFavoritesItemRecyclerViews() {
        binding.favRecView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesMealsAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.allMeal.observe(requireActivity(), Observer {
            favoritesMealsAdapter.differ.submitList(it)
            Log.d("FavoritFragment", "${it}")
        })
    }

}
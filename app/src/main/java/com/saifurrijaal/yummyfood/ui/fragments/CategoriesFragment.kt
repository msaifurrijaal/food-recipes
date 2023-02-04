package com.saifurrijaal.yummyfood.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.saifurrijaal.yummyfood.R
import com.saifurrijaal.yummyfood.adapter.CategoriesAdapter
import com.saifurrijaal.yummyfood.databinding.FragmentCategoriesBinding
import com.saifurrijaal.yummyfood.databinding.FragmentFavoritBinding
import com.saifurrijaal.yummyfood.ui.activities.MainActivity
import com.saifurrijaal.yummyfood.viewmodel.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewMOdel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoriesAdapter = CategoriesAdapter()
        viewMOdel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        observerCategoriesMeal()
    }

    private fun observerCategoriesMeal() {
        viewMOdel.categoriesList.observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setCategoriesList(it)
        })
    }

    private fun prepareRecyclerView() {
        binding.favoriteRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

}
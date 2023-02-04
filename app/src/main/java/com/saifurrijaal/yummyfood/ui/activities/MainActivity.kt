package com.saifurrijaal.yummyfood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saifurrijaal.yummyfood.R
import com.saifurrijaal.yummyfood.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}
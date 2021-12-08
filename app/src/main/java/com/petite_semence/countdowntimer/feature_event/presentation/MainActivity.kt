package com.petite_semence.countdowntimer.feature_event.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.petite_semence.countdowntimer.R
import com.petite_semence.countdowntimer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Timestamp
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupActionBarWithNavController(findNavController(R.id.fragmentContainerView))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun test(){
        val date = Date(2020-1900, 4, 26, 0, 0, 0)
        val timestamp = Timestamp(date.time)
        println(timestamp.time)
    }


}
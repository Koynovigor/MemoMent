package com.leonikl.memoment

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leonikl.memoment.view.MainViewModel
import com.leonikl.memoment.view.MainViewModelFactory
import com.leonikl.memoment.view.MyViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private val model = MyViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val owner = LocalViewModelStoreOwner.current
            owner?.let {
                viewModel = viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )
            }

            val allPages by viewModel.allPages.observeAsState(listOf())

            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "MainScreen"
            ){

                composable("MainScreen") {

                }
                composable("AddScreen"){

                }
                composable("CardScreen"){

                }
            }
        }
    }
}

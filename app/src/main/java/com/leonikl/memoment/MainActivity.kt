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
import com.leonikl.memoment.database.Page
import com.leonikl.memoment.screens.LoadingScreen
import com.leonikl.memoment.screens.MainScreen
import com.leonikl.memoment.screens.TextScreen
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
            var item = Page()
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "load"
            ){
                composable("load"){
                    LoadingScreen(
                        navController = navController
                    )
                }
                composable("MainScreen") {
                    MainScreen(
                        viewModel = viewModel,
                        model = model,
                        onClick = { task ->
                            item = task
                            navController.navigate("TextScreen")
                        }
                    )
                }
                composable("TextScreen"){
                    TextScreen(
                        viewModel = viewModel,
                        task = item,
                        navController = navController
                    )
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val a = viewModel.allPages.value
        for (i in a!!){
            if (i.delete){
                viewModel.deletePage(i)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val a = viewModel.allPages.value
        for (i in a!!){
            if (i.delete){
                viewModel.deletePage(i)
            }
        }
    }

}

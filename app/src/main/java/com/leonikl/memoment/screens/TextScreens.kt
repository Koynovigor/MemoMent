package com.leonikl.memoment.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.leonikl.memoment.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.leonikl.memoment.database.Page
import com.leonikl.memoment.view.MainViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextScreen(
    viewModel: MainViewModel,
    task: Page,
    navController: NavHostController
) {
    var titleScreen by remember {
        mutableStateOf(task.name)
    }
    var textScreen by remember {
        mutableStateOf(task.memo)
    }

    var save by remember {
        mutableStateOf(true)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.Transparent,
                    titleContentColor = Color.Transparent,
                    actionIconContentColor = Color.Transparent
                ),
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val maxChar = 22
                        BasicTextField(
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            singleLine = true,
                            value = titleScreen,
                            onValueChange = { text ->
                                if (text.length <= maxChar){
                                    titleScreen = text
                                    save = false
                                }
                            },
                        )
                    }
                },
                navigationIcon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = {
                                if ((textScreen != task.memo) or
                                    (titleScreen != task.name)
                                ) {
                                    task.memo = textScreen
                                    viewModel.updatePage(task)
                                }
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    }

                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            enabled = !save,
                            onClick = {
                                save = true
                                task.memo = textScreen
                                task.name = titleScreen
                                viewModel.updatePage(task)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Done,
                                contentDescription = "Save",
                                tint = if (save) Color.White else Color.Black
                            )
                        }
                        IconButton(
                            onClick = {
                                task.delete = true
                                viewModel.updatePage(task)
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete",
                                tint = Color.Black
                            )
                        }
                    }

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            BasicTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                value = textScreen,
                onValueChange = { text ->
                    textScreen = text
                    save = false
                }
            )
        }

    }
}
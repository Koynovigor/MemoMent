package com.leonikl.memoment.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.leonikl.memoment.database.Page

class MyViewModel:ViewModel() {
    var list = listOf<Page>()
}
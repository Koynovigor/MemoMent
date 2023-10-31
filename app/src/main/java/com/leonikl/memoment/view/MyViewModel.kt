package com.leonikl.memoment.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.leonikl.memoment.database.Page

class MyViewModel:ViewModel() {

    var enterPassword by mutableStateOf("")
    var createPassword by mutableStateOf("")
    var createPasswordRepeat by mutableStateOf("")

    var pass: Page = Page()
    var statePass by mutableStateOf(pass.memo)


    var enterText by mutableStateOf("")

    var savePage = Page()
}
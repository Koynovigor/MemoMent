package com.leonikl.memoment.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonikl.memoment.database.Page
import com.leonikl.memoment.database.PageRepository
import com.leonikl.memoment.database.PageRoomDatabase

class MainViewModel(application: Application) : ViewModel() {
    val allPages: LiveData<List<Page>>
    private val repository: PageRepository

    init {
        val pageDb = PageRoomDatabase.getInstance(application)
        val pageDao = pageDb.pageDao()

        repository = PageRepository(pageDao)
        allPages = repository.allPages
    }

    fun insertPage(page: Page) {
        repository.insertPage(page)
    }
    fun updatePage(page: Page) {
        repository.updatePage(page)
    }
    fun deletePage(page: Page) {
        repository.deletePage(page)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}
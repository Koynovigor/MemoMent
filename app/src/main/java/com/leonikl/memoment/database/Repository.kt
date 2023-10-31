package com.leonikl.memoment.database

import androidx.lifecycle.LiveData
import com.leonikl.memoment.database.Page
import com.leonikl.memoment.database.PageDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageRepository(private val pageDao: PageDao) {
    val allPages: LiveData<List<Page>> = pageDao.getAllPages()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertPage(newPage: Page) {
        coroutineScope.launch(Dispatchers.IO) {
            pageDao.insertPage(newPage)
        }
    }

    fun deletePage(page: Page) {
        coroutineScope.launch(Dispatchers.IO) {
            pageDao.deletePage(page)
        }
    }

    fun updatePage(page: Page){
        coroutineScope.launch(Dispatchers.IO) {
            pageDao.updatePage(page)
        }
    }
}

package com.riyaz.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riyaz.notes.core.model.Page
import com.riyaz.notes.core.router.RouteExecutionInterface
import com.riyaz.notes.core.router.StateUpdater
import com.riyaz.notes.util.PageUtil
import java.util.HashMap
import com.riyaz.notes.core.constant.Page as PageID

class MainViewModel: ViewModel() {
    private var _currentPage: MutableLiveData<Page> = MutableLiveData<Page>(Page(PageID.HP.id ,PageUtil.getPage(PageID.HP.id)))
    val currentPage: LiveData<Page> get() =  _currentPage

    val routeHandler: RouteExecutionInterface = object: RouteExecutionInterface{
        override fun executePage(pageId: String, parameters: HashMap<String, String>) {
            val fragment = PageUtil.getPage(pageId)
            val page = Page(pageId, fragment, parameters)
            _currentPage.postValue(page)
        }

        //use this to stuff on the page, like showing dialogue...
        override fun executeFunction() = Unit
    }

    val stateUpdater = object : StateUpdater(){
        override val routeHandler: RouteExecutionInterface
            get() = this@MainViewModel.routeHandler
    }
}

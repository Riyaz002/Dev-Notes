package com.riyaz.notes.core.router

abstract class StateUpdater {
    abstract val routeHandler: RouteExecutionInterface

    fun processStateUpdateLink(link: String?){
        if(link.isNullOrEmpty()) return
    }

    fun openPage(pageId: String, parameters: HashMap<String, String> = hashMapOf()){
        if (pageId != null) {
            routeHandler.executePage(
                pageId,
                parameters
            )
        }
    }
}
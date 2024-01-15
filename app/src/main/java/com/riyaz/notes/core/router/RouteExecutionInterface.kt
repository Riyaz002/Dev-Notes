package com.riyaz.notes.core.router

import java.util.HashMap

interface RouteExecutionInterface {
    fun executePage(pageId: String, parameters: HashMap<String, String> = hashMapOf())
    fun executeFunction()
}
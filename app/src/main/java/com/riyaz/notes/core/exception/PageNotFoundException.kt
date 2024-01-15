package com.riyaz.notes.core.exception

class PageNotFoundException(private val pageId: String): Exception() {
    override val message: String
        get() = this.javaClass.name + ": No such page exits (pageID: $pageId)"
}
package com.riyaz.notes.ui.dialoguefragment

interface MyDialogueCallbackListener {
    fun create(topic: String, description: String);
    fun cancel();
}
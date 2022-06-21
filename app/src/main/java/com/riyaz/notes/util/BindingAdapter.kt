package com.riyaz.notes.util


import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:truncatedText")
fun truncatedText(textView: TextView, text: String){
    textView.text =  if(text.length < 30) text
    else text.substring(0, 30) + "..."
}

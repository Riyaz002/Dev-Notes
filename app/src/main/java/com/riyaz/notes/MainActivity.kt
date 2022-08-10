package com.riyaz.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riyaz.notes.ui.homefragment.HomeFragment

const val HOME_FRAGMENT_TAG="HOME FRAGMENT"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_view, HomeFragment(), HOME_FRAGMENT_TAG).commit()
    }

}

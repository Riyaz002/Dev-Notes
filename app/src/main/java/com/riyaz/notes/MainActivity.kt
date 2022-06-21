package com.riyaz.notes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.riyaz.notes.ui.HomeFragment

const val HOME_FRAGMENT_TAG="HOME FRAGMENT"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_view, HomeFragment(), HOME_FRAGMENT_TAG).addToBackStack("homeFragment").commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1){
            supportFragmentManager.popBackStack()
        }
        super.onBackPressed()
    }
}

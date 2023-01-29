package com.ryadamir.recipesapp.ui.main

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ryadamir.recipesapp.R
import com.ryadamir.recipesapp.ui.settings.SettingsFragment
import com.ryadamir.recipesapp.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import nl.joery.animatedbottombar.AnimatedBottomBar

/**
 * This is the main class, it contains instantiations of bottom bar, fragments, and internet availability
 */

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setting the activity into full screen
        setFullScreen()
        // setting the bottom bar
        setBottomBar()
        // Internet live availability using LiveData
        InternetCheck()
        // Setting up home fragment
        setUpHomeFragment()

    }

    private fun setUpHomeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, HomeFragment())
            .commit()
    }

    private fun setBottomBar() {
        bottom_bar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                if (newIndex == 0) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, HomeFragment())
                        .commit()
                } else if (newIndex == 1) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, SettingsFragment())
                        .commit()
                }
            }

        })

    }

    private fun setFullScreen() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    private fun InternetCheck() {
        if (!Network.checkConnectivity(applicationContext))
            Toast.makeText(
                applicationContext,
                getString(R.string.internet_error),
                Toast.LENGTH_SHORT
            ).show()

    }
}
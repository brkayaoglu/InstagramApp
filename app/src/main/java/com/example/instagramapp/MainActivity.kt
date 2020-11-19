package com.example.instagramapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagramapp.Fragments.HomeFragment
import com.example.instagramapp.Fragments.NotificationsFragment
import com.example.instagramapp.Fragments.ProfileFragment
import com.example.instagramapp.Fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    var currentTheme : Boolean? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search -> {
                moveToFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_add_post -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {
                moveToFragment(NotificationsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                moveToFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        val sharedPref = this.getSharedPreferences("com.example.darktheme", Context.MODE_PRIVATE)

        currentTheme = sharedPref.getBoolean("NightMode", false)

        if(!currentTheme!!){
            setTheme(R.style.Theme_InstagramApp)
        }else{
            setTheme(R.style.Theme_DarkMode)
        }
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var profile = getIntent().getStringExtra("profileFragment")
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        if(profile == "profile"){
            val item: MenuItem? = navView.getMenu().findItem(R.id.nav_profile)
            item!!.setChecked(true)
            moveToFragment(ProfileFragment())
        }else
            moveToFragment(HomeFragment())
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun moveToFragment(fragment: Fragment?){
        val selectedFragment = supportFragmentManager.beginTransaction()
        selectedFragment.replace(R.id.fragment_container, fragment!!)
        selectedFragment.commit()
    }

    override fun onResume() {
        super.onResume()
        val theme = this.getSharedPreferences("com.example.darktheme", Context.MODE_PRIVATE).getBoolean("NightMode", false)
        if(currentTheme != theme){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("profileFragment", "profile")
            startActivity(intent)
            finish()
        }
    }
}
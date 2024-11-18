package com.example.simukk

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavActivity : AppCompatActivity() {

    var homeFragment = HomeFragment();
    var examinationFragment = ExaminationFragment()
    lateinit var session: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bottom_nav)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        var index = intent.getIntExtra("index", 0)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnApplyWindowInsetsListener(null);
        bottomNav.setOnItemSelectedListener {
                item ->
            when(item.itemId){
                R.id.nav_home -> {
                    replaceFragment(homeFragment)
                    true
                }
                R.id.nav_examination -> {
                    replaceFragment(examinationFragment)
                    true
                }
                else -> false
            }
        }

        if (index == 0)
        {
            replaceFragment(homeFragment)
        }else if(index == 1)
        {
            replaceFragment(examinationFragment)
            bottomNav.menu.findItem(R.id.nav_examination).isChecked = true
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
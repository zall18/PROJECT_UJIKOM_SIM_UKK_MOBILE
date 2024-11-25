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

class BottomStudentActivity : AppCompatActivity() {

    val homeStudentFragment = HomeStudentFragment()
    val profileFragment = ProfileFragment()
    lateinit var session: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bottom_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        var index = intent.getStringExtra("index")

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnApplyWindowInsetsListener(null);
        bottomNav.setOnItemSelectedListener {
                item ->
            when(item.itemId){
                R.id.nav_student_home-> {
                    replaceFragment(homeStudentFragment)
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(profileFragment)
                    true
                }
                else -> false
            }
        }

        if (index == "0")
        {
            replaceFragment(homeStudentFragment)
        }else if(index == "1")
        {
            replaceFragment(profileFragment)
            bottomNav.menu.findItem(R.id.nav_profile).isChecked = true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
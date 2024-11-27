package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simukk.Response.StudentProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentProfile : AppCompatActivity() {

    lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = "Bearer " + session.getString("token", "")
        val fullName: TextView = findViewById(R.id.fn_input)
        val username: TextView = findViewById(R.id.un_input)
        val email: TextView = findViewById(R.id.email_input)
        val phone: TextView = findViewById(R.id.phone_input)
        val nisn: TextView = findViewById(R.id.nisn_input)
        val grade: TextView = findViewById(R.id.gl_input)
        val major: TextView = findViewById(R.id.major_input)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val content: LinearLayout = findViewById(R.id.content)

        progressBar.visibility = View.VISIBLE
        content.visibility = View.GONE

        RetrofitClient.instance.studentProfile(token).enqueue(object :
            Callback<StudentProfileResponse>{
            override fun onResponse(
                call: Call<StudentProfileResponse>,
                response: Response<StudentProfileResponse>
            ) {
                val body = response.body()
                Log.d("Profile Response", "onResponse: $body")
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                if (response.isSuccessful){
                    if (body != null){
                        fullName.text = body.user.full_name
                        username.text = body.user.email
                        email.text = body.user.email
                        phone.text = body.user.phone
                        nisn.text = body.nisn
                        grade.text = body.grade_level.toString()
                        major.text = body.major.major_name

                    }
                }
            }

            override fun onFailure(call: Call<StudentProfileResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                t.printStackTrace()
            }
            })

        val back: ImageView = findViewById(R.id.back_profile)
        back.setOnClickListener {
            var intent = Intent(applicationContext, BottomStudentActivity::class.java)
            intent.putExtra("index", "1")
            startActivity(intent)
        }
    }
}
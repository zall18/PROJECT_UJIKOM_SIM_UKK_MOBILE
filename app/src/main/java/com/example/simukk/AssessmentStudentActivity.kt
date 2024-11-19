package com.example.simukk

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simukk.Model.Student
import com.example.simukk.Response.CompetencyStudentResponse
import retrofit2.Callback

class AssessmentStudentActivity : AppCompatActivity() {

    lateinit var session: SharedPreferences
    lateinit var studenModel: MutableList<Student>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_assessment_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val standardId = intent.getStringExtra("standard_id")
        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = session.getString("token", "")

        if (standardId != null) {
            RetrofitClient.instance.competencyStudent("Bearer $token", standardId).enqueue(object :
                Callback<CompetencyStudentResponse>)
        }

    }
}
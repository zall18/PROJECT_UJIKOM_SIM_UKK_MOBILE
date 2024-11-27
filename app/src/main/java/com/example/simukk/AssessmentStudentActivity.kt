package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simukk.Adapter.CompetencyStandardAdapter
import com.example.simukk.Adapter.CompetencyStudentAdapter
import com.example.simukk.Model.Student
import com.example.simukk.Response.CompetencyStudentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssessmentStudentActivity : AppCompatActivity() {

    lateinit var session: SharedPreferences
    lateinit var studentModel: MutableList<Student>
    lateinit var studentAdapter: CompetencyStudentAdapter

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
        studentModel = mutableListOf<Student>()
        val listview: ListView = findViewById(R.id.student_listview)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val content: LinearLayout = findViewById(R.id.content)

        val back: ImageView = findViewById(R.id.back_assessmentStudent)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, AssesmentActivity::class.java))
        }

        if (standardId != null) {
            progressBar.visibility = View.VISIBLE
            content.visibility = View.GONE
            RetrofitClient.instance.competencyStudent("Bearer $token", standardId).enqueue(object :
                Callback<CompetencyStudentResponse>{
                override fun onResponse(
                    call: Call<CompetencyStudentResponse>,
                    response: Response<CompetencyStudentResponse>
                ) {
                    val body = response.body()
                    Log.d("Assesment Response", "onResponse: $body")
                    progressBar.visibility = View.GONE
                    content.visibility = View.VISIBLE
                    if (response.isSuccessful)
                    {

                        if (body != null)
                        {
                            studentModel = body.competitor
                            studentAdapter = CompetencyStudentAdapter(applicationContext, studentModel, standardId)
                            listview.adapter = studentAdapter
                        }

                    }
                }

                override fun onFailure(call: Call<CompetencyStudentResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    content.visibility = View.VISIBLE
                    t.printStackTrace()
                }
                }
            )
        }

    }
}
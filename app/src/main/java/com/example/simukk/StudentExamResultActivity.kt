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
import com.example.simukk.Adapter.ResultStandardAdapter
import com.example.simukk.Adapter.StudentExamResultAdapter
import com.example.simukk.Model.StatusSummary
import com.example.simukk.Response.StudentExamResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentExamResultActivity : AppCompatActivity() {
    lateinit var session: SharedPreferences
    lateinit var summaryModel: MutableList<StatusSummary>
    lateinit var studentExamResultAdapter: StudentExamResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_exam_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = "Bearer " + session.getString("token", "")
        val listview: ListView = findViewById(R.id.es_listview)
        summaryModel = mutableListOf<StatusSummary>()

        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val content: LinearLayout = findViewById(R.id.content)

        progressBar.visibility = View.VISIBLE
        content.visibility = View.GONE

        RetrofitClient.instance.studentExamResult(token).enqueue(object :
            Callback<StudentExamResultResponse>{
            override fun onResponse(
                call: Call<StudentExamResultResponse>,
                response: Response<StudentExamResultResponse>
            ) {
                val body = response.body()
                Log.d("Exam Result Response", "onResponse: $body")
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                if (response.isSuccessful)
                {

                    if (body != null)
                    {
                        summaryModel = body.statusSummary
                        studentExamResultAdapter = StudentExamResultAdapter(applicationContext, summaryModel)
                        listview.adapter = studentExamResultAdapter
                    }
                }
            }

            override fun onFailure(call: Call<StudentExamResultResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                t.printStackTrace()
            }
            })

        val back: ImageView = findViewById(R.id.back_studentExam)
        back.setOnClickListener {
            var intent = Intent(applicationContext, BottomStudentActivity::class.java)
            intent.putExtra("index", "1")
            startActivity(intent)
        }
    }
}
package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simukk.Adapter.ExamResultAdapter
import com.example.simukk.Response.ExamResultResponse
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamResultStudentActivity : AppCompatActivity() {

    lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exam_result_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = session.getString("token", "")
        val standard_id = intent.getStringExtra("standard_id")
        val unit_title: TextView = findViewById(R.id.standard_title)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val content: LinearLayout = findViewById(R.id.content)

        val back: ImageView = findViewById(R.id.back_examresultstudent)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, ExamResultActivity::class.java))
        }
        progressBar.visibility = View.VISIBLE
        content.visibility = View.GONE
        if (standard_id != null)
        {
            RetrofitClient.instance.ExamResult("Bearer $token", standard_id).enqueue(object :
                Callback<ExamResultResponse>{
                override fun onResponse(
                    call: Call<ExamResultResponse>,
                    response: Response<ExamResultResponse>
                ) {
                    progressBar.visibility = View.GONE
                    content.visibility = View.VISIBLE
                    if (response.isSuccessful) {
                        val data = response.body()?.exam_result
                        unit_title.text = response.body()?.unit_title;
                        if (data != null) {
                            val examList = data.values.toList()

                            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                            recyclerView.layoutManager = LinearLayoutManager(this@ExamResultStudentActivity)
                            recyclerView.adapter = ExamResultAdapter(examList)
                        }
                    }
                }

                override fun onFailure(call: Call<ExamResultResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    content.visibility = View.VISIBLE
                    t.printStackTrace()
                }
                })
        }

    }
}
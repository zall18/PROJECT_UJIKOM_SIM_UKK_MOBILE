package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = session.getString("token", "")
        val standard_id = intent.getStringExtra("standard_id")
        val unit_title: TextView = findViewById(R.id.standard_title)

        val back: ImageView = findViewById(R.id.back_examresultstudent)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, ExamResultActivity::class.java))
        }

        if (standard_id != null)
        {
            RetrofitClient.instance.ExamResult("Bearer $token", standard_id).enqueue(object :
                Callback<ExamResultResponse>{
                override fun onResponse(
                    call: Call<ExamResultResponse>,
                    response: Response<ExamResultResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.exam_result
                        unit_title.text = response.body()?.unit_title;
                        if (data != null) {
                            // Loop setiap item di data
                            for ((_, exam) in data) {
                                val row = TableRow(this@ExamResultStudentActivity)

                                // Kolom Nama
                                val nameText = TextView(this@ExamResultStudentActivity)
                                nameText.text = exam.student_name
                                nameText.setPadding(8, 8, 8, 8)
                                nameText.setBackgroundResource(R.drawable.result_border)
                                row.addView(nameText)

                                // Kolom Status
                                val statusText = TextView(this@ExamResultStudentActivity)
                                statusText.text = exam.status
                                statusText.setPadding(8, 8, 8, 8)
                                statusText.setBackgroundResource(R.drawable.result_border)
                                statusText.setTextColor(if (exam.status == "Not Competent") Color.RED else Color.GREEN)
                                row.addView(statusText)

                                // Kolom Skor Akhir
                                val scoreText = TextView(this@ExamResultStudentActivity)
                                scoreText.text = exam.final_score.toString()
                                scoreText.setPadding(8, 8, 8, 8)
                                scoreText.setBackgroundResource(R.drawable.result_border)
                                row.addView(scoreText)

                                // Tambahkan Row ke TableLayout
                                tableLayout.addView(row)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ExamResultResponse>, t: Throwable) {
                    t.printStackTrace()
                }
                })
        }

    }
}
package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simukk.Adapter.ResultStandardAdapter
import com.example.simukk.Adapter.StandardAdapter
import com.example.simukk.Model.CompetencyStandard
import com.example.simukk.Response.ComeptencyStandardResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamResultActivity : AppCompatActivity() {

    lateinit var session: SharedPreferences
    lateinit var competencyModel: MutableList<CompetencyStandard>
    lateinit var standardAdapter: ResultStandardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exam_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = session.getString("token", "")

        competencyModel = mutableListOf<CompetencyStandard>()
        val listview: ListView = findViewById(R.id.assessment_listview)

        val back: ImageView = findViewById(R.id.back_examresult)
        back.setOnClickListener {
            var intent = Intent(applicationContext, BottomNavActivity::class.java)
            intent.putExtra("index", "1")
            startActivity(intent)
        }

        RetrofitClient.instance.competencyStandard("Bearer $token").enqueue(object :
            Callback<ComeptencyStandardResponse> {
            override fun onResponse(
                call: Call<ComeptencyStandardResponse>,
                response: Response<ComeptencyStandardResponse>
            ) {
                val body = response.body()
                Log.d("Assesment Response", "onResponse: $body")

                if (response.isSuccessful)
                {

                    if (body != null)
                    {
                        competencyModel = body.competency
                        standardAdapter = ResultStandardAdapter(applicationContext, competencyModel)
                        listview.adapter = standardAdapter
                    }
                }
            }

            override fun onFailure(call: Call<ComeptencyStandardResponse>, t: Throwable) {
                t.printStackTrace()
            }
        }

        )
    }
}
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
import com.example.simukk.Model.CompetencyStandard
import com.example.simukk.Response.ComeptencyStandardResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssesmentActivity : AppCompatActivity() {

    lateinit var session: SharedPreferences
    lateinit var competencyModel: MutableList<CompetencyStandard>
    lateinit var competencyStandardAdapter: CompetencyStandardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_assesment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = session.getString("token", "")
        val back: ImageView = findViewById(R.id.back_assessment)

        back.setOnClickListener {

            var intent = Intent(applicationContext, BottomNavActivity::class.java)
            intent.putExtra("index", "1")
            startActivity(intent)

        }

        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val content: LinearLayout = findViewById(R.id.content)

        progressBar.visibility = View.VISIBLE
        content.visibility = View.GONE

        competencyModel = mutableListOf<CompetencyStandard>()
        val listview: ListView = findViewById(R.id.assessment_listview)

        RetrofitClient.instance.competencyStandard("Bearer $token").enqueue(object :
            Callback<ComeptencyStandardResponse>{
            override fun onResponse(
                call: Call<ComeptencyStandardResponse>,
                response: Response<ComeptencyStandardResponse>
            ) {
                val body = response.body()
                Log.d("Assesment Response", "onResponse: $body")
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE

                if (response.isSuccessful)
                {

                    if (body != null)
                    {
                        competencyModel = body.competency
                        competencyStandardAdapter = CompetencyStandardAdapter(applicationContext, competencyModel)
                        listview.adapter = competencyStandardAdapter
                    }

                }
            }

            override fun onFailure(call: Call<ComeptencyStandardResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                t.printStackTrace()
            }
        }

        )
    }
}
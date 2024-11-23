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
import com.example.simukk.Adapter.CompetencyStudentAdapter
import com.example.simukk.Adapter.ElementStatusAdapter
import com.example.simukk.Model.ElementStatus
import com.example.simukk.Response.ElementStatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssessmentStatusActivity : AppCompatActivity() {

    lateinit var session: SharedPreferences
    lateinit var elementModel: MutableList<ElementStatus>
    lateinit var elementStatusAdapter: ElementStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_assessment_status)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        var standard_id = intent.getStringExtra("standard_id")
        var student_id = intent.getStringExtra("student_id")
        var token = session.getString("token", "")
        val listview: ListView = findViewById(R.id.ele_listview)
        elementModel = mutableListOf<ElementStatus>()

        val back: ImageView = findViewById(R.id.back_assessmentStatus)
        back.setOnClickListener {
            val intent = Intent(applicationContext, AssessmentStudentActivity::class.java)
            intent.putExtra("standard_id", standard_id)
            startActivity(intent)
        }

        if (standard_id != null && student_id != null){
            RetrofitClient.instance.elementStatus("Bearer $token", standard_id, student_id).enqueue(object :
                Callback<ElementStatusResponse>{
                override fun onResponse(
                    call: Call<ElementStatusResponse>,
                    response: Response<ElementStatusResponse>
                ) {
                    val body = response.body()
                    Log.d("Element Status Response", "onResponse: $body")

                    if (response.isSuccessful)
                    {

                        if (body != null)
                        {
                            if (token != null)
                            {
                                elementModel = body.elements
                                elementStatusAdapter = ElementStatusAdapter(applicationContext, elementModel, token, standard_id, student_id)
                                listview.adapter = elementStatusAdapter
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<ElementStatusResponse>, t: Throwable) {
                    t.printStackTrace()
                }
                })

        }

    }
}
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
import com.example.simukk.Adapter.ElementStatusAdapter
import com.example.simukk.Adapter.StandardElementAdapter
import com.example.simukk.Model.CompetencyElement
import com.example.simukk.Model.ElementStatus
import com.example.simukk.Response.StandardElementResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandardElement : AppCompatActivity() {

    lateinit var session: SharedPreferences
    lateinit var elementModel: MutableList<CompetencyElement>
    lateinit var elementStatusAdapter: StandardElementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_standard_element)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = session.getString("token", "")
        var standard_id = intent.getStringExtra("standard_id")
        val listview: ListView = findViewById(R.id.ele_listview)
        elementModel = mutableListOf<CompetencyElement>()

        val back: ImageView = findViewById(R.id.back_standardElement)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, StandardActivity::class.java))
        }

        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val content: LinearLayout = findViewById(R.id.content)

        progressBar.visibility = View.VISIBLE
        content.visibility = View.GONE

        if (standard_id != null)
        {
            RetrofitClient.instance.StandardElement("Bearer $token", standard_id).enqueue(object :
                Callback<StandardElementResponse>{
                override fun onResponse(
                    call: Call<StandardElementResponse>,
                    response: Response<StandardElementResponse>
                ) {
                    val body = response.body()
                    Log.d("Competency Response", "onResponse: $body")
                    progressBar.visibility = View.GONE
                    content.visibility = View.VISIBLE

                    if (response.isSuccessful)
                    {
                        if (body != null)
                        {
                            elementModel = body.elements
                            elementStatusAdapter = StandardElementAdapter(applicationContext, elementModel)
                            listview.adapter = elementStatusAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<StandardElementResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    content.visibility = View.VISIBLE
                    t.printStackTrace()
                }
                }
            )

        }
    }
}
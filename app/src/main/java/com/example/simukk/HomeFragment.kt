package com.example.simukk

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.simukk.Response.AssessorHomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = session.getString("token", "")
        val competencyCount: TextView = view.findViewById(R.id.home_competency_count)
        val competitorCount: TextView = view.findViewById(R.id.home_compe)
        val studentActive: TextView = view.findViewById(R.id.home_student)
        val avg: TextView = view.findViewById(R.id.home_avg)

        RetrofitClient.instance.assessorHome("Bearer $token").enqueue(object :
            Callback<AssessorHomeResponse>{
            override fun onResponse(
                call: Call<AssessorHomeResponse>,
                response: Response<AssessorHomeResponse>
            ) {
                val data = response.body()
                Log.d("Home Assessor Response", "onResponse: $data")

                if (response.isSuccessful)
                {
                    competencyCount.text = data?.competency_count.toString()
                    competitorCount.text = data?.competitor_count.toString()
                    studentActive.text = data?.student_active.toString()
                    avg.text = data?.avg_last_score.toString()

                }
            }

            override fun onFailure(call: Call<AssessorHomeResponse>, t: Throwable) {
                t.printStackTrace()
            }
            })

    }
}
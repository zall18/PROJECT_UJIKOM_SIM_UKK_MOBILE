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
        var competency_count: TextView = view.findViewById(R.id.competency_count)

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
                    competency_count.text = data?.competency_count.toString()
                }
            }

            override fun onFailure(call: Call<AssessorHomeResponse>, t: Throwable) {
                t.printStackTrace()
            }
            })

    }
}
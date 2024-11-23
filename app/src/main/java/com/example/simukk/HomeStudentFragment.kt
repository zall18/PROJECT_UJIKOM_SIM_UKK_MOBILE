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
import com.example.simukk.Response.StudentHomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeStudentFragment : Fragment() {


    lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val standardCount: TextView = view.findViewById(R.id.standard_count)
        val notCompetentCount: TextView = view.findViewById(R.id.notcompetent_count)
        val competentCount: TextView = view.findViewById(R.id.competent_count)
        val session = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = "Bearer " + session.getString("token", "")

        RetrofitClient.instance.studentHome(token).enqueue(object : Callback<StudentHomeResponse>{
            override fun onResponse(
                call: Call<StudentHomeResponse>,
                response: Response<StudentHomeResponse>
            ) {
                val body = response.body()
                Log.d("Student Home Response", "onResponse: $body")
                if (response.isSuccessful){
                    if (body != null){

                        standardCount.text = body.standardsCount.toString()
                        notCompetentCount.text = body.notCompetentCount.toString()
                        competentCount.text = (body.standardsCount - body.notCompetentCount).toString()

                    }
                }
            }

            override fun onFailure(call: Call<StudentHomeResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}
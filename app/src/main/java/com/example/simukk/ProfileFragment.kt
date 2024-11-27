package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.simukk.Model.User
import com.example.simukk.Response.LogoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = "Bearer " + session.getString("token", "")
        val name: TextView = view.findViewById(R.id.name_profile)
        val nisn: TextView = view.findViewById(R.id.nisn_profile)
        val examResutl: LinearLayout = view.findViewById(R.id.exam_result)
        val profile: LinearLayout = view.findViewById(R.id.edit_profile)

        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        val content: LinearLayout = view.findViewById(R.id.content)

        progressBar.visibility = View.VISIBLE
        content.visibility = View.GONE


        RetrofitClient.instance.me(token).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val body = response.body()
                Log.d("Me Response", "onResponse: $body")
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                if (response.isSuccessful){
                    name.text = body?.full_name
                    nisn.text = "NISN: " + body?.student?.nisn.toString()
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                t.printStackTrace()
            }
        })

        examResutl.setOnClickListener {
            startActivity(Intent(requireContext(), StudentExamResultActivity::class.java))
        }

        profile.setOnClickListener {
            startActivity(Intent(requireContext(), StudentProfile::class.java))
        }

        val logout: LinearLayout = view.findViewById(R.id.logout)
        logout.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            content.visibility = View.GONE
            RetrofitClient.instance.logout(token).enqueue(object : Callback<LogoutResponse>{
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    progressBar.visibility = View.GONE
                    content.visibility = View.VISIBLE
                    if (response.isSuccessful){
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    content.visibility = View.VISIBLE
                    t.printStackTrace()
                }
            })
        }
    }
}
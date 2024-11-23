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
import android.widget.TextView
import com.example.simukk.Model.User
import com.example.simukk.Response.LogoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminProfile : Fragment() {
    lateinit var session: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = "Bearer " + session.getString("token", "")
        val name: TextView = view.findViewById(R.id.name_profile)
        val type: TextView = view.findViewById(R.id.type_profile)
        val profile: LinearLayout = view.findViewById(R.id.edit_profile)

        RetrofitClient.instance.me(token).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val body = response.body()
                Log.d("Me Response", "onResponse: $body")
                if (response.isSuccessful){
                    name.text = body?.full_name
                    type.text = body?.assessor?.assessor_type
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })

        profile.setOnClickListener {
            startActivity(Intent(requireContext(), AssessorProfileActivity::class.java))
        }

        val logout: LinearLayout = view.findViewById(R.id.logout)
        logout.setOnClickListener {
            RetrofitClient.instance.logout(token).enqueue(object : Callback<LogoutResponse>{
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    if (response.isSuccessful){
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
}
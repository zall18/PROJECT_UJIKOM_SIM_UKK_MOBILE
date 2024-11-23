package com.example.simukk.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import android.widget.TextView
import com.example.simukk.Model.ElementStatus
import com.example.simukk.R
import com.example.simukk.Request.GradingRequest
import com.example.simukk.Response.GradingResponse
import com.example.simukk.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ElementStatusAdapter(val context: Context, val data: MutableList<ElementStatus>, val token: String, val standard_id: String, val student_id: String): BaseAdapter() {
    val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: inflater.inflate(R.layout.assesmentstatusitem, null, false)

        val title = view.findViewById<TextView>(R.id.ele_title)
        val competent = view.findViewById<RadioButton>(R.id.competent)
        val notCompetent = view.findViewById<RadioButton>(R.id.notcompetent)


        val data = getItem(position) as ElementStatus

        title.text = data.element
        if (data.status == "Kompeten")
        {
            competent.isChecked = true
            notCompetent.isChecked = false
        }else if (data.status == "Belum Kompeten"){
            notCompetent.isChecked = true
            competent.isChecked = false
        }else{
            competent.isChecked = false
            notCompetent.isChecked = false
        }

        competent.setOnClickListener {

            val gradingRequest = GradingRequest(standard_id, "1", "-")

            RetrofitClient.instance.grading(gradingRequest, "Bearer $token", data.element_id, student_id).enqueue(object :
                Callback<GradingResponse>{
                override fun onResponse(
                    call: Call<GradingResponse>,
                    response: Response<GradingResponse>
                ) {
                    val body = response.body()
                    Log.d("Grading Request", "onResponse: $body")
                }

                override fun onFailure(call: Call<GradingResponse>, t: Throwable) {
                    t.printStackTrace()
                }
                })

        }

        notCompetent.setOnClickListener {
            val gradingRequest = GradingRequest(standard_id, "0", "-")

            RetrofitClient.instance.grading(gradingRequest, "Bearer $token", data.element_id, student_id).enqueue(object :
                Callback<GradingResponse>{
                override fun onResponse(
                    call: Call<GradingResponse>,
                    response: Response<GradingResponse>
                ) {
                    val body = response.body()
                    if (response.isSuccessful)
                    {
                        Log.d("Grading Request", "onResponse: $body")
                    }
                }

                override fun onFailure(call: Call<GradingResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        return view
    }
}
package com.example.simukk.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.simukk.AssessmentStudentActivity
import com.example.simukk.ExamResultStudentActivity
import com.example.simukk.Model.CompetencyStandard
import com.example.simukk.R
import com.example.simukk.StandardElement

class ResultStandardAdapter(val context: Context, val data: MutableList<CompetencyStandard>): BaseAdapter() {

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
        val view = convertView ?: inflater.inflate(R.layout.competencyitem, null, false)

        val title = view.findViewById<TextView>(R.id.compe_title)
        val item = view.findViewById<LinearLayout>(R.id.compe_item)

        val data = getItem(position) as CompetencyStandard

        title.text = data.unit_title
        item.setOnClickListener {
            val intent = Intent(context, ExamResultStudentActivity::class.java)
            intent.putExtra("standard_id", data.id.toString())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        return view
    }
}
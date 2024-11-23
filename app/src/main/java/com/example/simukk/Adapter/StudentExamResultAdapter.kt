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
import com.example.simukk.Model.CompetencyStandard
import com.example.simukk.Model.StatusSummary
import com.example.simukk.R
import com.example.simukk.StandardElement
import org.w3c.dom.Text

class StudentExamResultAdapter(val context: Context, val data: MutableList<StatusSummary>): BaseAdapter() {

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
        val view = convertView ?: inflater.inflate(R.layout.statusexamitem, null, false)

        val standard = view.findViewById<TextView>(R.id.es_standard)
        val finalScore = view.findViewById<TextView>(R.id.es_finalscore)
        val status = view.findViewById<TextView>(R.id.es_status)

        val data = getItem(position) as StatusSummary

        standard.text = data.unit_title
        finalScore.text = "Final Score: " + data.final_score
        status.text = data.status

        return view
    }
}
package com.example.simukk.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.simukk.AssessmentStatusActivity
import com.example.simukk.AssessmentStudentActivity
import com.example.simukk.Model.Student
import com.example.simukk.R

class CompetencyStudentAdapter(val context: Context, val data: MutableList<Student>, val standard_id: String): BaseAdapter() {

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
        val view = convertView ?: inflater.inflate(R.layout.studentitem, null, false)

        val name = view.findViewById<TextView>(R.id.student_name)
        val item = view.findViewById<LinearLayout>(R.id.student_item)

        val data = getItem(position) as Student

        name.text = data.user.full_name
        item.setOnClickListener {
            val intent = Intent(context, AssessmentStatusActivity::class.java)
            intent.putExtra("student_id", data.id.toString())
            intent.putExtra("standard_id", standard_id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        return view
    }
}
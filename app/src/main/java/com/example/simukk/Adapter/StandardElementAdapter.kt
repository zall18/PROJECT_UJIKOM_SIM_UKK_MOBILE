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
import com.example.simukk.Model.CompetencyElement
import com.example.simukk.Model.CompetencyStandard
import com.example.simukk.R

class StandardElementAdapter(val context: Context, val data: MutableList<CompetencyElement>): BaseAdapter() {

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
        val view = convertView ?: inflater.inflate(R.layout.standardelementitem, null, false)

        val title = view.findViewById<TextView>(R.id.se_title)
        val item = view.findViewById<LinearLayout>(R.id.se_item)

        val data = getItem(position) as CompetencyElement

        title.text = data.criteria

        return view
    }
}
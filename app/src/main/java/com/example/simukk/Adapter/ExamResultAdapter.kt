package com.example.simukk.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simukk.Model.ExamDetail
import com.example.simukk.R

class ExamResultAdapter(private val examResults: List<ExamDetail>) :
    RecyclerView.Adapter<ExamResultAdapter.ExamResultViewHolder>() {

    class ExamResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.student_name)
        val status: TextView = itemView.findViewById(R.id.status)
        val finalScore: TextView = itemView.findViewById(R.id.final_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.examresultstudentitem, parent, false)
        return ExamResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExamResultViewHolder, position: Int) {
        val result = examResults[position]
        holder.studentName.text = result.student_name
        holder.status.text = result.status
        holder.status.setTextColor(
            if (result.status == "Not Competent") Color.RED else Color.GREEN
        )
        holder.finalScore.text = result.final_score.toString()
    }

    override fun getItemCount(): Int = examResults.size
}

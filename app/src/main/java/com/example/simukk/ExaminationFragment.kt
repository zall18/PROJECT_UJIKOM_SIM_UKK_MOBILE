package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class ExaminationFragment : Fragment() {

    lateinit var session: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_examination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val competencyStandard: LinearLayout = view.findViewById(R.id.competency_standard)
        val competencyAssessment: LinearLayout = view.findViewById(R.id.competency_assesment)
        val examResult: LinearLayout = view.findViewById(R.id.exam_result)

        competencyAssessment.setOnClickListener {
            startActivity(Intent(requireContext(), AssesmentActivity::class.java))
        }

        competencyStandard.setOnClickListener {
            startActivity(Intent(requireContext(), StandardActivity::class.java))
        }

        examResult.setOnClickListener {
            startActivity(Intent(requireContext(), ExamResultActivity::class.java))
        }

    }
}
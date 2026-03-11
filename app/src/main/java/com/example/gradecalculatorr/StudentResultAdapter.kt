package com.example.gradecalculatorr

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentResultAdapter(private val students: List<StudentResult>) :
    RecyclerView.Adapter<StudentResultAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tvStudentId)
        val tvName: TextView = view.findViewById(R.id.tvStudentName)
        val tvAverage: TextView = view.findViewById(R.id.tvAverage)
        val tvGrade: TextView = view.findViewById(R.id.tvGrade)
        val tvMarks: TextView = view.findViewById(R.id.tvMarks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.tvId.text = student.id
        holder.tvName.text = student.name
        holder.tvAverage.text = "Avg: ${"%.1f".format(student.average)}%"
        holder.tvGrade.text = student.grade
        holder.tvMarks.text = "Marks: ${student.marks.joinToString(", ") { "%.0f".format(it) }} | Total: ${"%.0f".format(student.total)}"

        val (bg, text) = gradeColor(student.grade)
        holder.tvGrade.setBackgroundColor(Color.parseColor(bg))
        holder.tvGrade.setTextColor(Color.parseColor(text))
    }

    override fun getItemCount() = students.size

    private fun gradeColor(grade: String): Pair<String, String> = when (grade) {
        "A"  -> "#1B5E20" to "#FFFFFF"
        "B+" -> "#2E7D32" to "#FFFFFF"
        "B"  -> "#388E3C" to "#FFFFFF"
        "C+" -> "#558B2F" to "#FFFFFF"
        "C"  -> "#F9A825" to "#000000"
        "D+" -> "#F57F17" to "#000000"
        "D"  -> "#E65100" to "#FFFFFF"
        "F"  -> "#B71C1C" to "#FFFFFF"
        else -> "#757575" to "#FFFFFF"
    }
}

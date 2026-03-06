package com.example.gradecalculatorr

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private val PICK_FILE_REQUEST = 1001
    private var studentResults: List<StudentResult> = emptyList()

    // Views
    private lateinit var btnUpload: Button
    private lateinit var btnDownload: Button
    private lateinit var emptyState: LinearLayout
    private lateinit var resultsLayout: LinearLayout
    private lateinit var tvDistribution: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnUpload = findViewById(R.id.btnUpload)
        btnDownload = findViewById(R.id.btnDownload)
        emptyState = findViewById(R.id.emptyState)
        resultsLayout = findViewById(R.id.resultsLayout)
        tvDistribution = findViewById(R.id.tvDistribution)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        btnUpload.setOnClickListener { openFilePicker() }
        btnDownload.setOnClickListener { downloadResult() }

        showEmptyState()
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Excel File"), PICK_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri -> processExcelFile(uri) }
        }
    }

    private fun processExcelFile(uri: Uri) {
        try {
            val inputStream: InputStream = contentResolver.openInputStream(uri)
                ?: throw Exception("Cannot open file")

            val workbook = WorkbookFactory.create(inputStream)
            val sheet = workbook.getSheetAt(0)
            val dataRows = mutableListOf<Row>()
            var headerFound = false

            for (i in 0..sheet.lastRowNum) {
                val row = sheet.getRow(i) ?: continue
                val firstCell = row.getCell(0)?.toString()?.trim() ?: continue
                if (firstCell.equals("Student ID", ignoreCase = true)) {
                    headerFound = true
                    for (j in (i + 1)..sheet.lastRowNum) {
                        val dataRow = sheet.getRow(j) ?: continue
                        val id = dataRow.getCell(0)?.toString()?.trim()
                        if (!id.isNullOrBlank()) dataRows.add(dataRow)
                    }
                    break
                }
            }

            if (!headerFound) {
                for (i in 0..sheet.lastRowNum) {
                    val row = sheet.getRow(i) ?: continue
                    val id = row.getCell(0)?.toString()?.trim()
                    if (!id.isNullOrBlank()) dataRows.add(row)
                }
            }

            workbook.close()
            inputStream.close()

            if (dataRows.isEmpty()) {
                showToast("This sheet has no entries")
                showEmptyState()
                return
            }

            studentResults = dataRows.map { row ->
                val studentId = row.getCell(0)?.toString()?.trim() ?: ""
                val studentName = row.getCell(1)?.toString()?.trim() ?: "Unknown"
                val marks = mutableListOf<Double>()
                for (col in 2 until row.lastCellNum) {
                    val cell = row.getCell(col)
                    if (cell != null && cell.cellType == CellType.NUMERIC) {
                        marks.add(cell.numericCellValue)
                    }
                }
                val average = if (marks.isNotEmpty()) marks.average() else 0.0
                StudentResult(
                    id = studentId,
                    name = studentName,
                    marks = marks,
                    total = marks.sum(),
                    average = average,
                    grade = calculateGrade(average)
                )
            }

            displayResults(studentResults)

        } catch (e: Exception) {
            showToast("Error reading file: ${e.message}")
        }
    }

    private fun calculateGrade(average: Double): String = when {
        average >= 80 -> "A"
        average >= 70 -> "B+"
        average >= 60 -> "B"
        average >= 55 -> "C+"
        average >= 50 -> "C"
        average >= 45 -> "D+"
        average >= 40 -> "D"
        else -> "F"
    }

    private fun displayResults(results: List<StudentResult>) {
        emptyState.visibility = View.GONE
        resultsLayout.visibility = View.VISIBLE
        btnDownload.visibility = View.VISIBLE

        val distribution = results.groupBy { it.grade }.mapValues { it.value.size }
        val distributionText = buildString {
            append("📊 Grade Distribution (${results.size} students):\n")
            listOf("A", "B+", "B", "C+", "C", "D+", "D", "F").forEach { grade ->
                val count = distribution[grade] ?: 0
                if (count > 0) append("  $grade: $count student${if (count > 1) "s" else ""}\n")
            }
        }
        tvDistribution.text = distributionText.trimEnd()
        recyclerView.adapter = StudentResultAdapter(results)
    }

    private fun showEmptyState() {
        emptyState.visibility = View.VISIBLE
        resultsLayout.visibility = View.GONE
        btnDownload.visibility = View.VISIBLE
    }

    private fun downloadResult() {
        if (studentResults.isEmpty()) { showToast("No results to export"); return }
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Grade Results")

            val headerStyle = workbook.createCellStyle()
            headerStyle.fillForegroundColor = XSSFColor(byteArrayOf(0x2E.toByte(), 0x7D.toByte(), 0x32.toByte()), null).index
            headerStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
            val headerFont = workbook.createFont()
            headerFont.bold = true
            headerFont.color = IndexedColors.WHITE.index
            headerStyle.setFont(headerFont)
            headerStyle.alignment = HorizontalAlignment.CENTER

            val headers = arrayOf("Student ID", "Student Name", "Total Marks", "Average (%)", "Grade")
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed { i, h ->
                headerRow.createCell(i).apply { setCellValue(h); cellStyle = headerStyle }
            }

            studentResults.forEachIndexed { idx, s ->
                val row = sheet.createRow(idx + 1)
                row.createCell(0).setCellValue(s.id)
                row.createCell(1).setCellValue(s.name)
                row.createCell(2).setCellValue(s.total)
                row.createCell(3).setCellValue(String.format("%.1f", s.average))
                row.createCell(4).setCellValue(s.grade)
            }

            val distSheet = workbook.createSheet("Grade Distribution")
            val distHeaders = arrayOf("Grade", "Count", "Percentage")
            val distHeaderRow = distSheet.createRow(0)
            distHeaders.forEachIndexed { i, h ->
                distHeaderRow.createCell(i).apply { setCellValue(h); cellStyle = headerStyle }
            }
            val dist = studentResults.groupBy { it.grade }.mapValues { it.value.size }
            listOf("A", "B+", "B", "C+", "C", "D+", "D", "F").forEachIndexed { idx, grade ->
                val count = dist[grade] ?: 0
                val row = distSheet.createRow(idx + 1)
                row.createCell(0).setCellValue(grade)
                row.createCell(1).setCellValue(count.toDouble())
                row.createCell(2).setCellValue(String.format("%.1f%%", count * 100.0 / studentResults.size))
            }

            for (i in 0..4) { sheet.autoSizeColumn(i); distSheet.autoSizeColumn(i) }

            val outputFile = File(getExternalFilesDir(null), "grade_results.xlsx")
            FileOutputStream(outputFile).use { workbook.write(it) }
            workbook.close()

            val uri = FileProvider.getUriForFile(this, "${packageName}.provider", outputFile)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, "Save Grade Results"))

        } catch (e: Exception) {
            showToast("Export failed: ${e.message}")
        }
    }

    private fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}
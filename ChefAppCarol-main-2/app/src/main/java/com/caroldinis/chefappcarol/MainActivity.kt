package com.caroldinis.chefappcarol

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var isLoggedIn = false // Chef Login State

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewCourses: ListView = findViewById(R.id.listViewCourses)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)

        val textViewTotalItems: TextView = findViewById(R.id.textViewTotalItems)

        // Configure Login Button
        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, LOGIN_REQUEST_CODE)
        }

        // Display courses and average prices
        updateCourseList(listViewCourses, textViewTotalItems)

        listViewCourses.setOnItemClickListener { _, _, position, _ ->
            val course = MenuData.getCoursesWithAveragePrices()[position].first
            val intent = Intent(this, CourseDetailActivity::class.java)
            intent.putExtra("course", course)
            intent.putExtra("isLoggedIn", isLoggedIn)
            startActivity(intent)
        }
    }

    // Refresh the course list and total items each time the activity resumes
    override fun onResume() {
        super.onResume()
        val listViewCourses: ListView = findViewById(R.id.listViewCourses)
        val textViewTotalItems: TextView = findViewById(R.id.textViewTotalItems)
        updateCourseList(listViewCourses, textViewTotalItems)
    }

    // Update UI after login
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK) {
            isLoggedIn = true
            findViewById<Button>(R.id.buttonLogin).visibility = Button.GONE
        }
    }

    // Show courses with average prices and update total items count
    private fun updateCourseList(listView: ListView, textViewTotalItems: TextView) {
        val coursesWithAverages = MenuData.getCoursesWithAveragePrices()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            coursesWithAverages.map { (course, averagePrice) ->
                "$course - Avg Price: ${averagePrice?.let { "$%.2f".format(it) } ?: "N/A"}"
            }
        )
        listView.adapter = adapter

        // Update total items count
        val totalItems = MenuData.getTotalDishesCount()
        textViewTotalItems.text = "Total items: $totalItems"
    }

    companion object {
        private const val LOGIN_REQUEST_CODE = 100
    }
}

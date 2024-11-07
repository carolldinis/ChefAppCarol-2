package com.caroldinis.chefappcarol

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class CourseDetailActivity : AppCompatActivity() {
    private lateinit var course: String
    private var isLoggedIn = false // Track login status for dish addition


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        course = intent.getStringExtra("course") ?: ""
        isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)

        val listViewDishes: ListView = findViewById(R.id.listViewDishes)
        val buttonAddNewDish: Button = findViewById(R.id.buttonAddNewDish)

        // Display list of dishes and enable/disable "Add" button based on login status
        updateDishList(listViewDishes)
        buttonAddNewDish.isEnabled = isLoggedIn

        buttonAddNewDish.setOnClickListener {
            if (isLoggedIn) {
                val intent = Intent(this, AddMenuItemActivity::class.java)
                intent.putExtra("selected_course", course)
                startActivityForResult(intent, ADD_DISH_REQUEST_CODE)
            } else {
                Toast.makeText(this, "Please log in to add a dish.", Toast.LENGTH_SHORT).show()
            }
        }

        // Implements removing dishes from the menu when long pressing on the item
        // listViewDishes.setOnItemLongClickListener { _, _, position, _ ->
        //  val dish = MenuData.getDishesByCourse(course)[position]
        //  MenuData.removeDish(dish)
        //  updateDishList(listViewDishes)
        //  Toast.makeText(this, "${dish.name} removed from menu.", Toast.LENGTH_SHORT).show()
        //  true
        // }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_DISH_REQUEST_CODE && resultCode == RESULT_OK) {
            updateDishList(findViewById(R.id.listViewDishes))
        }
    }

    private fun updateDishList(listView: ListView) {
        val dishes = MenuData.getDishesByCourse(course)
        val adapter = DishListAdapter(this, dishes) { dish ->
            MenuData.removeDish(dish)
            updateDishList(listView)
            Toast.makeText(this, "${dish.name} removed from menu.", Toast.LENGTH_SHORT).show()
        }
        listView.adapter = adapter
    }
    companion object {
        private const val ADD_DISH_REQUEST_CODE = 101
    }
}
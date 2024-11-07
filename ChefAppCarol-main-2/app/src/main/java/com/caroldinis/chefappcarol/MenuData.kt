package com.caroldinis.chefappcarol

object MenuData {
    private val menuList = mutableListOf<MenuItem>()

    init {
        // Add sample dishes
        menuList.add(MenuItem("Bruschetta", "Toasted bread with tomatoes", "Starter", 5.0))
        menuList.add(MenuItem("Grilled Chicken", "Juicy grilled chicken with herbs", "Main Course", 12.0))
        menuList.add(MenuItem("Cheesecake", "Rich cheesecake with strawberry sauce", "Dessert", 6.0))
    }

    // Adds a dish to the list
    fun addDish(dish: MenuItem) {
        menuList.add(dish)
    }

    // Removes a dish from the list
    fun removeDish(dish: MenuItem) {
        menuList.remove(dish)
    }

    // Returns the total number of dishes
    fun getDishes(): List<MenuItem> {
        return menuList
    }

    // Returns the total number of dishes
    fun getTotalDishesCount(): Int {
        return menuList.size
    }

    fun getDishesByCourse(course: String): List<MenuItem> {
        return menuList.filter { it.course == course }
    }

    // Returns a list of courses with their average price (or null if no dishes are in that course)
    fun getCoursesWithAveragePrices(): List<Pair<String, Double?>> {
        val courses = listOf("Starter", "Main Course", "Dessert") // Predefined courses

        return courses.map { course ->
            val courseDishes = menuList.filter { it.course == course }
            val averagePrice = if (courseDishes.isNotEmpty()) {
                courseDishes.sumOf { it.price } / courseDishes.size
            } else {
                null
            }
            course to averagePrice
        }
    }
}

data class MenuItem(
    val name: String,
    val description: String,
    val course: String,
    val price: Double
)
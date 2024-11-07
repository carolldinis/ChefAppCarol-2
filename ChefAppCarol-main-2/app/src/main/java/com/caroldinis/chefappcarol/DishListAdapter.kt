package com.caroldinis.chefappcarol

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class DishListAdapter(
    private val context: Context,
    private val dishes: List<MenuItem>,
    private val onRemoveClick: (MenuItem) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = dishes.size
    override fun getItem(position: Int): Any = dishes[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.dish_list_item, parent, false)
        val dishName = view.findViewById<TextView>(R.id.textDishName)
        val dishDescription = view.findViewById<TextView>(R.id.textDishDescription)
        val dishPrice = view.findViewById<TextView>(R.id.textDishPrice)
        val buttonRemove = view.findViewById<Button>(R.id.buttonRemoveDish)

        val dish = dishes[position]
        dishName.text = dish.name
        dishDescription.text = dish.description
        dishPrice.text = "$%.2f".format(dish.price)

        buttonRemove.setOnClickListener {
            onRemoveClick(dish)
        }

        return view
    }
}

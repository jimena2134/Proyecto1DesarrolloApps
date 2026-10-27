package proyecto.utn.qhambron.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proyecto.utn.qhambron.R
import proyecto.utn.qhambron.domain.model.Restaurant

/**
 * RecyclerView adapter that binds restaurant data items to views in the place list.
 */
class RestaurantAdapter(
    private val restaurantList: List<Restaurant>,
    private val onRestaurantClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val ivPlaceImage: ImageView =
            itemView.findViewById(R.id.ivPlaceImage)

        val tvPlaceName: TextView =
            itemView.findViewById(R.id.tvPlaceName)

        val tvFoodType: TextView =
            itemView.findViewById(R.id.tvFoodType)

        val tvRating: TextView =
            itemView.findViewById(R.id.tvRating)

        val tvProvince: TextView =
            itemView.findViewById(R.id.tvProvince)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_place,
                parent,
                false
            )

        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RestaurantViewHolder,
        position: Int
    ) {
        val restaurant = restaurantList[position]

        holder.tvPlaceName.text = restaurant.name
        holder.tvFoodType.text = restaurant.foodType

        if (restaurant.rating > 0f) {
            holder.tvRating.text = "★ ${restaurant.rating}"
            holder.tvRating.visibility = View.VISIBLE
        } else {
            holder.tvRating.visibility = View.GONE
        }

        holder.tvProvince.text =
            "● ${formatProvince(restaurant.province.name)}"

        if (restaurant.photoUris.isEmpty()) {
            holder.ivPlaceImage.setImageResource(
                R.drawable.loguito
            )
        }

        holder.itemView.setOnClickListener {
            onRestaurantClick(restaurant)
        }
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    private fun formatProvince(province: String): String {
        return province
            .lowercase()
            .replace("_", " ")
            .replaceFirstChar {
                it.uppercase()
            }
    }
}
package proyecto.utn.qhambron

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import proyecto.utn.qhambron.data.memory.RestaurantMemoryManager
import proyecto.utn.qhambron.viewmodel.RestaurantViewModel

/**
 * Activity that displays the details of a selected restaurant and lets the user
 * view its location, edit it or delete it.
 */
class PlaceDetailActivity : AppCompatActivity() {

    private lateinit var ivPlacePhoto: ImageView
    private lateinit var tvPlaceName: TextView
    private lateinit var tvPlaceRating: TextView
    private lateinit var btnFoodTypeTag: MaterialButton
    private lateinit var btnProvinceTag: MaterialButton
    private lateinit var btnStatusTag: MaterialButton
    private lateinit var tvPriceRange: TextView
    private lateinit var tvPlaceDescription: TextView
    private lateinit var restaurantViewModel: RestaurantViewModel
    private var restaurantId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_place_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        restaurantViewModel = RestaurantViewModel(RestaurantMemoryManager())

        ivPlacePhoto = findViewById<ImageView>(R.id.ivPlacePhoto)
        tvPlaceName = findViewById<TextView>(R.id.tvPlaceName)
        tvPlaceRating = findViewById<TextView>(R.id.tvPlaceRating)
        btnFoodTypeTag = findViewById<MaterialButton>(R.id.btnFoodTypeTag)
        btnProvinceTag = findViewById<MaterialButton>(R.id.btnProvinceTag)
        btnStatusTag = findViewById<MaterialButton>(R.id.btnStatusTag)
        tvPriceRange = findViewById<TextView>(R.id.tvPriceRange)
        tvPlaceDescription = findViewById<TextView>(R.id.tvPlaceDescription)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnViewLocation = findViewById<MaterialButton>(R.id.btnViewLocation)
        val btnEditPlace = findViewById<MaterialButton>(R.id.btnEditPlace)
        val btnDeletePlace = findViewById<MaterialButton>(R.id.btnDeletePlace)

        loadPlaceData()

        btnBack.setOnClickListener(View.OnClickListener{ view ->
            finish()
        })

        btnViewLocation.setOnClickListener(View.OnClickListener{ view ->
            openLocation()
        })

        btnEditPlace.setOnClickListener(View.OnClickListener{ view ->
            openEditPlace()
        })

        btnDeletePlace.setOnClickListener(View.OnClickListener{ view ->
            showDeleteDialog()
        })
    }

    private fun loadPlaceData(){
        try {
            restaurantId = intent.getStringExtra("RESTAURANT_ID") ?: ""
            tvPlaceName.text = intent.getStringExtra("RESTAURANT_NAME") ?: ""
            tvPlaceRating.text = getString(R.string.rating_format,
                intent.getFloatExtra("RATING", 0f))
            tvPlaceDescription.text = intent.getStringExtra("DESCRIPTION") ?: ""

            btnFoodTypeTag.text = intent.getStringExtra("FOOD_TYPE") ?: ""
            btnProvinceTag.text = getProvinceText(intent.getStringExtra("PROVINCE"))
            btnStatusTag.text = getStatusText(intent.getStringExtra("STATUS"))

            btnFoodTypeTag.isVisible = btnFoodTypeTag.text.isNotEmpty()
            btnProvinceTag.isVisible = btnProvinceTag.text.isNotEmpty()
            btnStatusTag.isVisible = btnStatusTag.text.isNotEmpty()

            if (intent.hasExtra("MINIMUM_PRICE") && intent.hasExtra("MAXIMUM_PRICE")){
                tvPriceRange.text = getString(R.string.price_range_format,
                    intent.getDoubleExtra("MINIMUM_PRICE", 0.0),
                    intent.getDoubleExtra("MAXIMUM_PRICE", 0.0))
            }else{
                tvPriceRange.isVisible = false
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun getProvinceText(province: String?): String{
        return when (province) {
            "SAN_JOSE" -> getString(R.string.san_jose)
            "ALAJUELA" -> getString(R.string.alajuela)
            "CARTAGO" -> getString(R.string.cartago)
            "HEREDIA" -> getString(R.string.heredia)
            "GUANACASTE" -> getString(R.string.guanacaste)
            "PUNTARENAS" -> getString(R.string.puntarenas)
            "LIMON" -> getString(R.string.limon)
            else -> ""
        }
    }

    private fun getStatusText(status: String?): String{
        return when (status) {
            "VISITED" -> getString(R.string.visited)
            "WANT_TO_VISIT" -> getString(R.string.want_to_visit)
            else -> ""
        }
    }

    private fun openEditPlace(){
        val editIntent = Intent(this, PlaceActivity::class.java)
        editIntent.putExtras(intent)
        editIntent.putExtra("EDIT_MODE", true)
        startActivity(editIntent)
    }

    private fun openLocation(){
        val locationIntent = Intent(this, LocationActivity::class.java)
        locationIntent.putExtra("SELECTION_MODE", false)
        locationIntent.putExtra("RESTAURANT_NAME", tvPlaceName.text.toString())
        locationIntent.putExtra("LOCATION_NAME", intent.getStringExtra("LOCATION_NAME"))
        startActivity(locationIntent)
    }

    private fun showDeleteDialog(){
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_delete_place, null)

        val tvDeleteMessage = dialogView.findViewById<TextView>(R.id.tvDeleteMessage)
        val btnCancelDelete = dialogView.findViewById<MaterialButton>(R.id.btnCancelDelete)
        val btnConfirmDelete = dialogView.findViewById<MaterialButton>(R.id.btnConfirmDelete)

        tvDeleteMessage.text = getString(R.string.delete_place_message,
            tvPlaceName.text.toString())

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnCancelDelete.setOnClickListener(View.OnClickListener{ view ->
            dialog.dismiss()
        })

        btnConfirmDelete.setOnClickListener(View.OnClickListener{ view ->
            dialog.dismiss()
            deletePlace()
        })

        dialog.show()
    }

    private fun deletePlace(){
        try {
            restaurantViewModel.removeRestaurant(restaurantId)
            Toast.makeText(this, getString(R.string.delete_success),
                Toast.LENGTH_LONG).show()
            finish()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }
}
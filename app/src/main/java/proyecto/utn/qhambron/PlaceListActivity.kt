package proyecto.utn.qhambron

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import proyecto.utn.qhambron.adapter.RestaurantAdapter
import proyecto.utn.qhambron.domain.model.Restaurant

/**
 * Activity that displays and manages the list of saved restaurants and places.
 */
class PlaceListActivity : AppCompatActivity() {

    private lateinit var btnHome: LinearLayout

    private lateinit var btnFoodFilter: MaterialButton
    private lateinit var btnProvinceFilter: MaterialButton
    private lateinit var btnStatusFilter: MaterialButton

    private lateinit var fabAddPlace: FloatingActionButton

    private lateinit var rvPlaces: RecyclerView
    private lateinit var restaurantAdapter: RestaurantAdapter

    private val restaurantList =
        mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_place_list)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        initComponents()
        setupRecyclerView()
        setupListeners()
    }

    private fun initComponents() {
        btnHome = findViewById(R.id.btnHome)

        btnFoodFilter =
            findViewById(R.id.btnFoodFilter)

        btnProvinceFilter =
            findViewById(R.id.btnProvinceFilter)

        btnStatusFilter =
            findViewById(R.id.btnStatusFilter)

        fabAddPlace =
            findViewById(R.id.fabAddPlace)

        rvPlaces =
            findViewById(R.id.rvPlaces)
    }

    private fun setupRecyclerView() {

        restaurantAdapter = RestaurantAdapter(
            restaurantList
        ) { restaurant ->

            openEditRestaurant(restaurant)
        }

        rvPlaces.layoutManager =
            LinearLayoutManager(this)

        rvPlaces.adapter =
            restaurantAdapter
    }

    private fun setupListeners() {

        btnHome.setOnClickListener {
            finish()
        }

        fabAddPlace.setOnClickListener {
            val intent =
                Intent(this, PlaceActivity::class.java)

            startActivity(intent)
        }

        btnFoodFilter.setOnClickListener {
            showFoodFilter()
        }

        btnProvinceFilter.setOnClickListener {
            showProvinceFilter()
        }

        btnStatusFilter.setOnClickListener {
            showStatusFilter()
        }
    }

    private fun openEditRestaurant(
        restaurant: Restaurant
    ) {

        val intent =
            Intent(this, PlaceActivity::class.java)

        intent.putExtra("EDIT_MODE", true)

        intent.putExtra(
            "RESTAURANT_ID",
            restaurant.id
        )

        intent.putExtra(
            "RESTAURANT_NAME",
            restaurant.name
        )

        intent.putExtra(
            "FOOD_TYPE",
            restaurant.foodType
        )

        intent.putExtra(
            "PROVINCE",
            restaurant.province.name
        )

        intent.putExtra(
            "STATUS",
            restaurant.status.name
        )

        intent.putExtra(
            "DESCRIPTION",
            restaurant.description
        )

        intent.putExtra(
            "RATING",
            restaurant.rating
        )

        restaurant.priceRange?.let {
            intent.putExtra(
                "MINIMUM_PRICE",
                it.minimumPrice
            )

            intent.putExtra(
                "MAXIMUM_PRICE",
                it.maximumPrice
            )
        }

        startActivity(intent)
    }

    private fun showFoodFilter() {

        val options =
            resources.getStringArray(
                R.array.food_filter_options
            )

        showFilterDialog(
            getString(
                R.string.select_food_type
            ),
            options,
            btnFoodFilter
        )
    }

    private fun showProvinceFilter() {

        val options =
            resources.getStringArray(
                R.array.province_filter_options
            )

        showFilterDialog(
            getString(
                R.string.select_province
            ),
            options,
            btnProvinceFilter
        )
    }

    private fun showStatusFilter() {

        val options =
            resources.getStringArray(
                R.array.status_filter_options
            )

        showFilterDialog(
            getString(
                R.string.select_status
            ),
            options,
            btnStatusFilter
        )
    }

    private fun showFilterDialog(
        title: String,
        options: Array<String>,
        button: MaterialButton
    ) {

        AlertDialog.Builder(this)
            .setTitle(title)
            .setItems(options) { dialog, position ->

                button.text =
                    options[position]

                dialog.dismiss()
            }
            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, _ ->

                dialog.dismiss()
            }
            .show()
    }
}

package proyecto.utn.qhambron

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
import proyecto.utn.qhambron.data.memory.RestaurantMemoryManager
import proyecto.utn.qhambron.domain.model.Province
import proyecto.utn.qhambron.domain.model.Restaurant
import proyecto.utn.qhambron.domain.model.RestaurantFilter
import proyecto.utn.qhambron.domain.model.RestaurantStatus
import proyecto.utn.qhambron.viewmodel.RestaurantViewModel

/**
 * Activity that displays, searches and filters restaurants.
 */
class PlaceListActivity : AppCompatActivity() {

    private lateinit var restaurantViewModel: RestaurantViewModel

    private lateinit var btnHome: LinearLayout

    private lateinit var btnFoodFilter: MaterialButton
    private lateinit var btnProvinceFilter: MaterialButton
    private lateinit var btnStatusFilter: MaterialButton

    private lateinit var etSearchPlaces: EditText
    private lateinit var fabAddPlace: FloatingActionButton

    private lateinit var rvPlaces: RecyclerView
    private lateinit var restaurantAdapter: RestaurantAdapter

    private val restaurantList = mutableListOf<Restaurant>()

    private var selectedFoodType: String? = null
    private var selectedProvince: Province? = null
    private var selectedStatus: RestaurantStatus? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_place_list)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->

            val systemBars = insets.getInsets(
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

        restaurantViewModel = RestaurantViewModel(
            RestaurantMemoryManager()
        )

        initComponents()
        setupRecyclerView()
        setupListeners()
        loadRestaurants()
    }

    override fun onResume() {
        super.onResume()

        if (::restaurantViewModel.isInitialized &&
            ::restaurantAdapter.isInitialized) {
            loadRestaurants()
        }
    }

    private fun initComponents() {

        btnHome = findViewById(R.id.btnHome)

        btnFoodFilter = findViewById(R.id.btnFoodFilter)
        btnProvinceFilter = findViewById(R.id.btnProvinceFilter)
        btnStatusFilter = findViewById(R.id.btnStatusFilter)

        etSearchPlaces = findViewById(R.id.etSearchPlaces)

        fabAddPlace = findViewById(R.id.fabAddPlace)

        rvPlaces = findViewById(R.id.rvPlaces)
    }

    private fun setupRecyclerView() {

        restaurantAdapter = RestaurantAdapter(
            restaurantList
        ) { restaurant ->

            openEditRestaurant(restaurant)
        }

        rvPlaces.layoutManager = LinearLayoutManager(this)

        rvPlaces.adapter = restaurantAdapter
    }

    private fun setupListeners() {

        btnHome.setOnClickListener {
            finish()
        }

        fabAddPlace.setOnClickListener {

            val intent = Intent(
                this,
                PlaceActivity::class.java
            )

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

        etSearchPlaces.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    loadRestaurants()
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )
    }

    private fun loadRestaurants() {

        val filter = RestaurantFilter(
            status = selectedStatus,
            province = selectedProvince,
            foodType = selectedFoodType
        )

        val searchText = etSearchPlaces.text
            .toString()
            .trim()

        val filteredRestaurants = restaurantViewModel
            .filterRestaurants(filter)
            .filter { restaurant ->

                restaurant.name.contains(
                    searchText,
                    ignoreCase = true
                )
            }
            .sortedBy { restaurant ->
                restaurant.name.lowercase()
            }

        restaurantList.clear()
        restaurantList.addAll(filteredRestaurants)

        restaurantAdapter.notifyDataSetChanged()
    }

    private fun openEditRestaurant(
        restaurant: Restaurant
    ) {

        val intent = Intent(
            this,
            PlaceDetailActivity::class.java
        )

        intent.putExtra(
            "RESTAURANT_ID",
            restaurant.id
        )

        startActivity(intent)
    }

    private fun showFoodFilter() {

        val options = resources.getStringArray(
            R.array.food_filter_options
        )

        AlertDialog.Builder(this)
            .setTitle(R.string.select_food_type)
            .setItems(options) { _, position ->

                selectedFoodType =
                    if (position == 0) {
                        null
                    } else {
                        options[position]
                    }

                btnFoodFilter.text =
                    if (position == 0) {
                        getString(R.string.food_filter)
                    } else {
                        options[position]
                    }

                loadRestaurants()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showProvinceFilter() {

        val options = resources.getStringArray(
            R.array.province_filter_options
        )

        AlertDialog.Builder(this)
            .setTitle(R.string.select_province)
            .setItems(options) { _, position ->

                selectedProvince =
                    if (position == 0) {
                        null
                    } else {
                        Province.values()[position - 1]
                    }

                btnProvinceFilter.text =
                    if (position == 0) {
                        getString(R.string.province)
                    } else {
                        options[position]
                    }

                loadRestaurants()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showStatusFilter() {

        val options = resources.getStringArray(
            R.array.status_filter_options
        )

        AlertDialog.Builder(this)
            .setTitle(R.string.select_status)
            .setItems(options) { _, position ->

                selectedStatus =
                    if (position == 0) {
                        null
                    } else {
                        RestaurantStatus.values()[position - 1]
                    }

                btnStatusFilter.text =
                    if (position == 0) {
                        getString(R.string.status)
                    } else {
                        options[position]
                    }

                loadRestaurants()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}


package proyecto.utn.qhambron

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import proyecto.utn.qhambron.data.memory.RestaurantMemoryManager
import proyecto.utn.qhambron.domain.model.PriceRange
import proyecto.utn.qhambron.domain.model.Province
import proyecto.utn.qhambron.domain.model.Restaurant
import proyecto.utn.qhambron.domain.model.RestaurantStatus
import proyecto.utn.qhambron.viewmodel.RestaurantViewModel
import java.util.UUID

/**
 * Activity for creating and editing restaurants.
 */
class PlaceActivity : AppCompatActivity() {

    private var isEditMode = false
    private var restaurantId = ""

    private lateinit var restaurantViewModel: RestaurantViewModel

    private lateinit var tvFormTitle: TextView

    private lateinit var etRestaurantName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etMinimumPrice: EditText
    private lateinit var etMaximumPrice: EditText

    private lateinit var spFoodType: Spinner
    private lateinit var spProvince: Spinner
    private lateinit var spStatus: Spinner

    private lateinit var ratingBar: RatingBar

    private lateinit var btnBack: ImageButton
    private lateinit var btnSelectLocation: MaterialButton
    private lateinit var btnSave: MaterialButton
    private lateinit var btnCamera: MaterialButton
    private lateinit var btnGallery: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_place)

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
        setupSpinners()
        checkMode()
        setupListeners()
    }

    private fun initComponents() {

        tvFormTitle = findViewById(R.id.tvFormTitle)

        etRestaurantName = findViewById(R.id.etRestaurantName)
        etDescription = findViewById(R.id.etDescription)
        etMinimumPrice = findViewById(R.id.etMinimumPrice)
        etMaximumPrice = findViewById(R.id.etMaximumPrice)

        spFoodType = findViewById(R.id.spFoodType)
        spProvince = findViewById(R.id.spProvince)
        spStatus = findViewById(R.id.spStatus)

        ratingBar = findViewById(R.id.ratingBar)

        btnBack = findViewById(R.id.btnBack)
        btnSelectLocation = findViewById(R.id.btnSelectLocation)
        btnSave = findViewById(R.id.btnSave)
        btnCamera = findViewById(R.id.btnCamera)
        btnGallery = findViewById(R.id.btnGallery)
    }

    private fun setupSpinners() {

        setupSpinner(
            spFoodType,
            R.array.food_type_options
        )

        setupSpinner(
            spProvince,
            R.array.province_options
        )

        setupSpinner(
            spStatus,
            R.array.status_options
        )
    }

    private fun setupSpinner(
        spinner: Spinner,
        arrayResource: Int
    ) {

        val adapter = ArrayAdapter.createFromResource(
            this,
            arrayResource,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner.adapter = adapter
    }

    private fun checkMode() {

        isEditMode = intent.getBooleanExtra(
            "EDIT_MODE",
            false
        )

        restaurantId = intent.getStringExtra(
            "RESTAURANT_ID"
        ) ?: ""

        if (isEditMode) {
            setupEditMode()
        } else {
            setupAddMode()
        }
    }

    private fun setupAddMode() {

        tvFormTitle.text = getString(R.string.add_place)

        btnSelectLocation.text =
            getString(R.string.select_location)

        btnSave.text = getString(R.string.save_place)
    }

    private fun setupEditMode() {

        tvFormTitle.text = getString(R.string.edit_place)

        btnSelectLocation.text =
            getString(R.string.change_location)

        btnSave.text = getString(R.string.save_changes)

        loadRestaurantData()
    }

    private fun loadRestaurantData() {

        val restaurant = restaurantViewModel
            .getRestaurantById(restaurantId)

        if (restaurant == null) {

            Toast.makeText(
                this,
                getString(R.string.restaurant_not_found),
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        etRestaurantName.setText(restaurant.name)
        etDescription.setText(restaurant.description)

        ratingBar.rating = restaurant.rating

        restaurant.priceRange?.let {

            etMinimumPrice.setText(
                it.minimumPrice.toString()
            )

            etMaximumPrice.setText(
                it.maximumPrice.toString()
            )
        }

        selectSpinnerItem(
            spFoodType,
            restaurant.foodType
        )

        spProvince.setSelection(
            restaurant.province.ordinal + 1
        )

        spStatus.setSelection(
            restaurant.status.ordinal
        )
    }

    private fun selectSpinnerItem(
        spinner: Spinner,
        value: String
    ) {

        for (index in 0 until spinner.count) {

            if (
                spinner.getItemAtPosition(index)
                    .toString()
                    .equals(value, ignoreCase = true)
            ) {

                spinner.setSelection(index)
                return
            }
        }
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            saveRestaurant()
        }

        btnCamera.setOnClickListener {

            Toast.makeText(
                this,
                getString(R.string.take_photo),
                Toast.LENGTH_SHORT
            ).show()
        }

        btnGallery.setOnClickListener {

            Toast.makeText(
                this,
                getString(R.string.select_from_gallery),
                Toast.LENGTH_SHORT
            ).show()
        }

        btnSelectLocation.setOnClickListener {

            Toast.makeText(
                this,
                btnSelectLocation.text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveRestaurant() {

        if (!isValidData()) {
            return
        }

        if (isEditMode) {
            showUpdateDialog()
        } else {
            createRestaurant()
        }
    }

    private fun createRestaurant() {

        val restaurant = buildRestaurant(
            UUID.randomUUID().toString()
        )

        restaurantViewModel.saveRestaurant(restaurant)

        Toast.makeText(
            this,
            getString(R.string.save_success),
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }

    private fun showUpdateDialog() {

        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_update_title)
            .setMessage(R.string.confirm_update_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                updateRestaurant()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun updateRestaurant() {

        val existingRestaurant = restaurantViewModel
            .getRestaurantById(restaurantId)

        if (existingRestaurant == null) {

            Toast.makeText(
                this,
                getString(R.string.restaurant_not_found),
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val formRestaurant = buildRestaurant(restaurantId)

        val updatedRestaurant = formRestaurant.copy(
            photoUris = existingRestaurant.photoUris,
            location = existingRestaurant.location
        )

        restaurantViewModel.updateRestaurant(
            updatedRestaurant
        )

        Toast.makeText(
            this,
            getString(R.string.update_success),
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }

    private fun buildRestaurant(id: String): Restaurant {

        val name = etRestaurantName.text
            .toString()
            .trim()

        val foodType = spFoodType.selectedItem
            .toString()

        val province = Province.values()[
            spProvince.selectedItemPosition - 1
        ]

        val status = RestaurantStatus.values()[
            spStatus.selectedItemPosition
        ]

        val description = etDescription.text
            .toString()
            .trim()

        val minimumPrice = etMinimumPrice.text
            .toString()
            .toDoubleOrNull()

        val maximumPrice = etMaximumPrice.text
            .toString()
            .toDoubleOrNull()

        val priceRange =
            if (minimumPrice != null && maximumPrice != null) {
                PriceRange(minimumPrice, maximumPrice)
            } else {
                null
            }

        return Restaurant(
            id = id,
            name = name,
            foodType = foodType,
            province = province,
            description = description,
            priceRange = priceRange,
            rating = ratingBar.rating,
            status = status,
            photoUris = emptyList(),
            location = null
        )
    }

    private fun isValidData(): Boolean {

        if (
            etRestaurantName.text.toString()
                .trim().isEmpty()
        ) {

            etRestaurantName.error =
                getString(R.string.error_required_name)

            etRestaurantName.requestFocus()

            return false
        }

        if (spFoodType.selectedItemPosition == 0) {

            Toast.makeText(
                this,
                getString(R.string.error_required_food_type),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        if (spProvince.selectedItemPosition == 0) {

            Toast.makeText(
                this,
                getString(R.string.error_required_province),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        val minimumPrice = etMinimumPrice.text
            .toString()
            .toDoubleOrNull()

        val maximumPrice = etMaximumPrice.text
            .toString()
            .toDoubleOrNull()

        if (
            (minimumPrice == null) !=
            (maximumPrice == null)
        ) {

            Toast.makeText(
                this,
                getString(R.string.error_incomplete_price),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        if (
            minimumPrice != null &&
            maximumPrice != null &&
            minimumPrice > maximumPrice
        ) {

            Toast.makeText(
                this,
                getString(R.string.error_invalid_price),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        return true
    }
}

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

/**
 * Activity for creating, editing, and managing restaurant place details and form inputs.
 */
class PlaceActivity : AppCompatActivity() {

    private var isEditMode = false

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
        setupSpinners()
        checkMode()
        setupListeners()
    }

    private fun initComponents() {

        tvFormTitle =
            findViewById(R.id.tvFormTitle)

        etRestaurantName =
            findViewById(R.id.etRestaurantName)

        etDescription =
            findViewById(R.id.etDescription)

        etMinimumPrice =
            findViewById(R.id.etMinimumPrice)

        etMaximumPrice =
            findViewById(R.id.etMaximumPrice)

        spFoodType =
            findViewById(R.id.spFoodType)

        spProvince =
            findViewById(R.id.spProvince)

        spStatus =
            findViewById(R.id.spStatus)

        ratingBar =
            findViewById(R.id.ratingBar)

        btnBack =
            findViewById(R.id.btnBack)

        btnSelectLocation =
            findViewById(R.id.btnSelectLocation)

        btnSave =
            findViewById(R.id.btnSave)

        btnCamera =
            findViewById(R.id.btnCamera)

        btnGallery =
            findViewById(R.id.btnGallery)
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

        val adapter =
            ArrayAdapter.createFromResource(
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

        isEditMode =
            intent.getBooleanExtra(
                "EDIT_MODE",
                false
            )

        if (isEditMode) {
            setupEditMode()
        } else {
            setupAddMode()
        }
    }

    private fun setupAddMode() {

        tvFormTitle.text =
            getString(R.string.add_place)

        btnSelectLocation.text =
            getString(R.string.select_location)

        btnSave.text =
            getString(R.string.save_place)
    }

    private fun setupEditMode() {

        tvFormTitle.text =
            getString(R.string.edit_place)

        btnSelectLocation.text =
            getString(R.string.change_location)

        btnSave.text =
            getString(R.string.save_changes)

        loadRestaurantData()
    }

    private fun loadRestaurantData() {

        etRestaurantName.setText(
            intent.getStringExtra(
                "RESTAURANT_NAME"
            ) ?: ""
        )

        etDescription.setText(
            intent.getStringExtra(
                "DESCRIPTION"
            ) ?: ""
        )

        ratingBar.rating =
            intent.getFloatExtra(
                "RATING",
                0f
            )

        if (
            intent.hasExtra(
                "MINIMUM_PRICE"
            )
        ) {
            etMinimumPrice.setText(
                intent.getDoubleExtra(
                    "MINIMUM_PRICE",
                    0.0
                ).toString()
            )
        }

        if (
            intent.hasExtra(
                "MAXIMUM_PRICE"
            )
        ) {
            etMaximumPrice.setText(
                intent.getDoubleExtra(
                    "MAXIMUM_PRICE",
                    0.0
                ).toString()
            )
        }

        selectSpinnerItem(
            spFoodType,
            intent.getStringExtra(
                "FOOD_TYPE"
            )
        )

        selectProvince(
            intent.getStringExtra(
                "PROVINCE"
            )
        )

        selectStatus(
            intent.getStringExtra(
                "STATUS"
            )
        )
    }

    private fun selectSpinnerItem(
        spinner: Spinner,
        value: String?
    ) {

        if (value == null) {
            return
        }

        for (
        index in 0 until spinner.count
        ) {

            if (
                spinner.getItemAtPosition(index)
                    .toString()
                    .equals(
                        value,
                        ignoreCase = true
                    )
            ) {

                spinner.setSelection(index)
                return
            }
        }
    }

    private fun selectProvince(
        province: String?
    ) {

        val position =
            when (province) {

                "SAN_JOSE" -> 1
                "ALAJUELA" -> 2
                "CARTAGO" -> 3
                "HEREDIA" -> 4
                "GUANACASTE" -> 5
                "PUNTARENAS" -> 6
                "LIMON" -> 7

                else -> 0
            }

        spProvince.setSelection(position)
    }

    private fun selectStatus(
        status: String?
    ) {

        val position =
            when (status) {

                "VISITED" -> 0
                "WANT_TO_VISIT" -> 1

                else -> 0
            }

        spStatus.setSelection(position)
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
                getString(
                    R.string.select_from_gallery
                ),
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

        val name =
            etRestaurantName.text
                .toString()
                .trim()

        val foodType =
            spFoodType.selectedItem
                .toString()

        val province =
            spProvince.selectedItem
                .toString()

        val status =
            spStatus.selectedItem
                .toString()

        val description =
            etDescription.text
                .toString()
                .trim()

        val minimumPrice =
            etMinimumPrice.text
                .toString()
                .toDoubleOrNull()

        val maximumPrice =
            etMaximumPrice.text
                .toString()
                .toDoubleOrNull()

        val rating =
            ratingBar.rating

        if (isEditMode) {

            Toast.makeText(
                this,
                getString(
                    R.string.update_success
                ),
                Toast.LENGTH_SHORT
            ).show()

        } else {

            Toast.makeText(
                this,
                getString(
                    R.string.save_success
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isValidData(): Boolean {

        if (
            etRestaurantName.text
                .toString()
                .trim()
                .isEmpty()
        ) {

            etRestaurantName.error =
                getString(
                    R.string.error_required_name
                )

            etRestaurantName.requestFocus()

            return false
        }

        if (
            spFoodType.selectedItemPosition == 0
        ) {

            Toast.makeText(
                this,
                getString(
                    R.string.error_required_food_type
                ),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        if (
            spProvince.selectedItemPosition == 0
        ) {

            Toast.makeText(
                this,
                getString(
                    R.string.error_required_province
                ),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        val minimumPrice =
            etMinimumPrice.text
                .toString()
                .toDoubleOrNull()

        val maximumPrice =
            etMaximumPrice.text
                .toString()
                .toDoubleOrNull()

        if (
            minimumPrice != null &&
            maximumPrice != null &&
            minimumPrice > maximumPrice
        ) {

            Toast.makeText(
                this,
                getString(
                    R.string.error_invalid_price
                ),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        return true
    }
}
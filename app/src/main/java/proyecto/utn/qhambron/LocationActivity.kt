package proyecto.utn.qhambron

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton

/**
 * Activity that shows the location of a restaurant (view mode)
 * or lets the user choose a location (selection mode).
 */
class LocationActivity : AppCompatActivity() {

    private lateinit var tvLocationTitle: TextView
    private lateinit var tvRestaurantName: TextView
    private lateinit var tvAddress: TextView
    private lateinit var layoutViewMode: LinearLayout
    private lateinit var layoutSelectMode: LinearLayout
    private var isSelectionMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_location)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvLocationTitle = findViewById<TextView>(R.id.tvLocationTitle)
        tvRestaurantName = findViewById<TextView>(R.id.tvRestaurantName)
        tvAddress = findViewById<TextView>(R.id.tvAddress)
        layoutViewMode = findViewById<LinearLayout>(R.id.layoutViewMode)
        layoutSelectMode = findViewById<LinearLayout>(R.id.layoutSelectMode)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnConfirmLocation = findViewById<MaterialButton>(R.id.btnConfirmLocation)

        checkMode()

        btnBack.setOnClickListener(View.OnClickListener{ view ->
            finish()
        })

        btnConfirmLocation.setOnClickListener(View.OnClickListener{ view ->
            confirmLocation()
        })
    }

    private fun checkMode(){
        isSelectionMode = intent.getBooleanExtra("SELECTION_MODE", false)

        if (isSelectionMode)
            showSelectionMode()
        else
            showViewMode()
    }

    private fun showViewMode(){
        tvLocationTitle.text = getString(R.string.map_title)
        layoutViewMode.isVisible = true
        layoutSelectMode.isVisible = false

        tvRestaurantName.text = intent.getStringExtra("RESTAURANT_NAME") ?: ""
        tvAddress.text = intent.getStringExtra("LOCATION_NAME")
            ?: getString(R.string.no_address)
    }

    private fun showSelectionMode(){
        tvLocationTitle.text = getString(R.string.select_location)
        layoutViewMode.isVisible = false
        layoutSelectMode.isVisible = true
    }

    private fun confirmLocation(){
        try {
            Toast.makeText(this, getString(R.string.location_confirmed),
                Toast.LENGTH_LONG).show()
            finish()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }
}
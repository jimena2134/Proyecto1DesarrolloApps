package proyecto.utn.qhambron

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

/**
 * Main activity representing the home screen with navigation to place list and add place screens.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var btnPlaces: LinearLayout
    private lateinit var btnAddPlace: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        btnPlaces = findViewById(R.id.btnPlaces)
        btnAddPlace = findViewById(R.id.btnAddPlace)

        btnPlaces.setOnClickListener {
            val intent = Intent(this, PlaceListActivity::class.java)
            startActivity(intent)
        }

        btnAddPlace.setOnClickListener {
            val intent = Intent(this, PlaceActivity::class.java)
            startActivity(intent)
        }
    }
}
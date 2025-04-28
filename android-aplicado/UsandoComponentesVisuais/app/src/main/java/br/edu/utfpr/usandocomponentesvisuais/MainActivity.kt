package br.edu.utfpr.usandocomponentesvisuais

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RatingBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var window: LinearLayout
    private lateinit var ratingBar: RatingBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window = findViewById(R.id.main)
        ratingBar = findViewById(R.id.ratingBar)
    }

    fun onTestComponentClick(view: View) {
        Snackbar.make(window, "Avaliação: ${ratingBar.rating}", Snackbar.LENGTH_LONG).show()
    }
}

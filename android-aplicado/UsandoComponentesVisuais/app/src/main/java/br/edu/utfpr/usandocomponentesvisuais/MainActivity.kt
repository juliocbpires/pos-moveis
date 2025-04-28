package br.edu.utfpr.usandocomponentesvisuais

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var window: LinearLayout
    private lateinit var datePicker: DatePicker

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
        datePicker = findViewById(R.id.datePicker)
    }

    fun onTestComponentClick(view: View) {
        val formattededDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
        Snackbar.make(window, "Data: $formattededDate", Snackbar.LENGTH_LONG).show()
    }
}

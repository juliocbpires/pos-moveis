package br.edu.utfpr.usandocomponentesvisuais

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var window: LinearLayout
    private lateinit var editText: AutoCompleteTextView

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
        editText = findViewById(R.id.editText)

        val cities = listOf(
            "Goiânia",
            "Passa Quatro",
            "Brasília",
            "Curitiba",
            "Pato Branco"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)
        editText.setAdapter(adapter)

        editText.threshold = 2
    }

    fun onClickButton(view: View) {
        Snackbar.make(window, editText.text, Snackbar.LENGTH_LONG).show()
    }
}

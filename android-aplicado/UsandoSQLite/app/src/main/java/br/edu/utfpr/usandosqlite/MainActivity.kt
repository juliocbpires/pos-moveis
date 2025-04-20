package br.edu.utfpr.usandosqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite.entity.Entry

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setButtonsListeners()

        database = DatabaseHandler(this)
    }

    private fun setButtonsListeners() {
        binding.btInsert.setOnClickListener {
            btInsertOnClick()
        }

        binding.btUpdate.setOnClickListener {
            btUpdateOnClick()
        }

        binding.btList.setOnClickListener {
            btListOnClick()
        }

        binding.btDelete.setOnClickListener {
            btDeleteOnClick()
        }

        binding.btSearch.setOnClickListener {
            btSearchOnClick()
        }
    }

    private fun btInsertOnClick() {
        val entry = Entry(0, binding.etName.text.toString(), binding.etPhone.text.toString())
        database.insert(entry)
        Toast.makeText(this, "Registro inserido com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btUpdateOnClick() {
         val entry = Entry(binding.etCod.text.toString().toInt(),
            binding.etName.text.toString(), binding.etPhone.text.toString())

        database.update(entry)

        Toast.makeText(this, "Registro alterado com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btDeleteOnClick() {
        database.delete(binding.etCod.text.toString().toInt())

        Toast.makeText(this, "Registro excluído com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btListOnClick() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
//        val cursor = database.list()
//        val output = StringBuilder()
//
//        while (cursor.moveToNext()) {
//            output.append("Nome: ${cursor.getString(1)} - ")
//            output.append("Telefone: ${cursor.getString(2)}\n")
//        }
//
//        Toast.makeText(this, output.toString(), Toast.LENGTH_LONG).show()
    }

    private fun btSearchOnClick() {
        val entry: Entry? = database.search(binding.etCod.text.toString().toInt())

        if (entry != null) {
            binding.etName.setText(entry.name)
            binding.etPhone.setText(entry.phone)
        } else Toast.makeText(this, "Registro não encontrado!", Toast.LENGTH_LONG).show()
    }
}

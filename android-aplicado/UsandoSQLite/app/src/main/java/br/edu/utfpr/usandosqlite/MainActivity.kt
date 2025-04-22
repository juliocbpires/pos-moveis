package br.edu.utfpr.usandosqlite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

        initData()

        database = DatabaseHandler(this)
    }

    private fun initData() {
        if (intent.getIntExtra("id", 0) != 0) {
            binding.etCod.setText(intent.getIntExtra("id", 0).toString())
            binding.etName.setText(intent.getStringExtra("name"))
            binding.etPhone.setText(intent.getStringExtra("phone"))
        } else {
            binding.btSearch.visibility = View.GONE
            binding.btDelete.visibility = View.GONE
        }
    }

    private fun setButtonsListeners() {

        binding.btUpdate.setOnClickListener {
            btUpdateOnClick()
        }

        binding.btDelete.setOnClickListener {
            btDeleteOnClick()
        }

        binding.btSearch.setOnClickListener {
            btSearchOnClick()
        }
    }

    private fun btUpdateOnClick() {
        if (binding.etCod.text.toString().isEmpty()) {
            val entry = Entry(0, binding.etName.text.toString(), binding.etPhone.text.toString())
            database.insert(entry)
            Toast.makeText(this, "Registro inserido com sucesso!", Toast.LENGTH_LONG).show()
            finish()
        } else {

            val entry = Entry(binding.etCod.text.toString().toInt(),
                binding.etName.text.toString(), binding.etPhone.text.toString())

            database.update(entry)

            Toast.makeText(this, "Registro alterado com sucesso!", Toast.LENGTH_LONG).show()

            finish()
        }
    }

    private fun btDeleteOnClick() {
        database.delete(binding.etCod.text.toString().toInt())

        Toast.makeText(this, "Registro excluído com sucesso!", Toast.LENGTH_LONG).show()

        finish()
    }

    private fun btListOnClick() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    private fun btSearchOnClick() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Código da Pessoa")
        val input = EditText(this)
        builder.setView(input)
        builder.setCancelable(false)
        builder.setNegativeButton("Fechar", null)
        builder.setPositiveButton("Pesquisar", { dialogInterface, i ->
            val entry: Entry? = database.search(input.text.toString().toInt())

            if (entry != null) {
                binding.etCod.setText(input.text.toString())
                binding.etName.setText(entry.name)
                binding.etPhone.setText(entry.phone)
            } else Toast.makeText(this, "Registro não encontrado!", Toast.LENGTH_LONG).show()
        })
        builder.show()
        }
}

package br.edu.utfpr.fluxocaixa

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var db : DatabaseHandler

    private lateinit var spinnerTipo : Spinner
    private lateinit var spinnerDetalhe : Spinner
    private lateinit var editTextValor : EditText
    private lateinit var editTextData : EditText

    private lateinit var buttonLancar : Button
    private lateinit var buttonVerLancamentos : Button
    private lateinit var buttonSaldo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinnerTipo = findViewById(R.id.spinnerTipo)
        spinnerDetalhe = findViewById(R.id.spinnerDetalhe)
        editTextValor = findViewById(R.id.editTextValor)
        editTextData = findViewById(R.id.editTextData)

        buttonLancar = findViewById(R.id.buttonLancar)
        buttonVerLancamentos = findViewById(R.id.buttonVerLancamentos)
        buttonSaldo = findViewById(R.id.buttonSaldo)

        val detalhesCredito = listOf("Salário", "Extras")
        val detalhesDebito = listOf("Alimentação", "Transporte", "Saúde", "Moradia")

        spinnerTipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("Crédito", "Débito"))

        spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val detalhes = if (position == 0) detalhesCredito else detalhesDebito
                spinnerDetalhe.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, detalhes)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        editTextData.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val dataSelecionada = "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                    editTextData.setText(dataSelecionada)
                },
                year, month, day
            )

            datePicker.show()
        }

        db = DatabaseHandler(this)

        buttonLancar.setOnClickListener {
            val valor = editTextValor.text.toString()
            val data = editTextData.text.toString()

            if (valor.isEmpty() || data.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val lancamento = Lancamentos(
                0,
                spinnerTipo.selectedItem.toString(),
                spinnerDetalhe.selectedItem.toString(),
                valor.toDouble(),
                data
            )

            db.insert(lancamento)

            Toast.makeText(this, "Lançamento cadastrado com sucesso!", Toast.LENGTH_LONG).show()
        }

        buttonVerLancamentos.setOnClickListener {
            val intent = Intent(this, LancamentosActivity::class.java)
            startActivity(intent)
        }

        buttonSaldo.setOnClickListener {
            val lancamentos = db.list()
            val totalCredito = lancamentos.filter { it.tipo == "Crédito" }.sumOf { it.valor }
            val totalDebito = lancamentos.filter { it.tipo == "Débito" }.sumOf { it.valor }
            val saldo = totalCredito - totalDebito

            val dialog = android.app.AlertDialog.Builder(this)
                .setTitle("Saldo")
                .setMessage("Saldo atual: R$ %.2f".format(saldo))
                .setPositiveButton("OK", null)
                .create()

            dialog.show()
        }
    }
}

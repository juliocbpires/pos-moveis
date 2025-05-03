package br.edu.utfpr.fluxocaixa

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LancamentosActivity : AppCompatActivity() {
    private lateinit var listaLancamentos : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lancamentos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registros)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listaLancamentos = findViewById(R.id.listaLancamentos)
        val db = DatabaseHandler(this)
        val lancamentos = db.list()

        if (lancamentos.isEmpty()) {
            Toast.makeText(this, "Nenhum lançamento encontrado.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recuperarArrayString(lancamentos))
        listaLancamentos.adapter = adapter
    }

    private fun recuperarArrayString(lancamentos: MutableList<Lancamentos>) : MutableList<String> {
        val listaNome = mutableListOf<String>()
        for (lancamento in lancamentos) {
            listaNome.add("${lancamento.tipo[0]} ${lancamento.data} - ${lancamento.detalhe} - ${lancamento.valor}")
        }
        return listaNome
    }
}

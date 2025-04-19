package br.edu.utfpr.usandosqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setButtonsListeners()

        database = SQLiteDatabase.openOrCreateDatabase(
            this.getDatabasePath("dbfile.sqlite"), null )

        database.execSQL("CREATE TABLE IF NOT EXISTS cadastro " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, telefone TEXT)")
    }

    private fun setButtonsListeners() {
        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }

        binding.btAlterar.setOnClickListener {
            btAlterarOnClick()
        }

        binding.btListar.setOnClickListener {
            btListarOnClick()
        }

        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }

        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }
    }

    private fun btIncluirOnClick() {
        val registry = ContentValues()
        registry.put("nome", binding.etNome.text.toString())
        registry.put("telefone", binding.etTelefone.text.toString())

        database.insert("cadastro", null, registry)

        Toast.makeText(this, "Registro inserido com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btAlterarOnClick() {
        val registry = ContentValues()
        registry.put("nome", binding.etNome.text.toString())
        registry.put("telefone", binding.etTelefone.text.toString())

        database.update("cadastro",
            registry,"_id=${binding.etCod.text}", null)

        Toast.makeText(this, "Registro alterado com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btExcluirOnClick() {
        database.delete("cadastro",
        "_id=${binding.etCod.text}", null)

        Toast.makeText(this, "Registro excluído com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btListarOnClick() {
        val cursor = database.query("cadastro", null, null, null, null, null, null)
        val output = StringBuilder()
        while (cursor.moveToNext()) {
            output.append(cursor.getString(NOME))
            output.append(" - ")
            output.append(cursor.getString(TELEFONE))
            output.append("\n")
        }
        Toast.makeText(this, output.toString(), Toast.LENGTH_LONG).show()
    }

    private fun btPesquisarOnClick() {
        val registry = database.query("cadastro",
            null, "_id=?", arrayOf(binding.etCod.text.toString()), null, null, null)

        if (registry.moveToNext()) {
            binding.etNome.setText(registry.getString(NOME))
            binding.etTelefone.setText(registry.getString(TELEFONE))
        } else Toast.makeText(this, "Registro não encontrado!", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val ID = 0
        private const val NOME = 1
        private const val TELEFONE = 2
    }
}

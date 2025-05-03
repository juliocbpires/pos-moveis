package br.edu.utfpr.fluxocaixa

import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: android.content.Context) : SQLiteOpenHelper(context, "fluxocaixa.db", null, 1) {
    override fun onCreate(db: android.database.sqlite.SQLiteDatabase) {
        val createTable = ("CREATE TABLE lancamentos (_id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, detalhe TEXT, valor REAL, data TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: android.database.sqlite.SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS lancamentos")
        onCreate(db)
    }

    fun insert(lancamento: Lancamentos) {
        val db = this.writableDatabase

        val contentValues = android.content.ContentValues()
        contentValues.put("tipo", lancamento.tipo)
        contentValues.put("detalhe", lancamento.detalhe)
        contentValues.put("valor", lancamento.valor)
        contentValues.put("data", lancamento.data)
        db.insert("lancamentos", null, contentValues)
    }

    fun list() : MutableList<Lancamentos> {
        val db = this.readableDatabase
        val cursor = db.query("lancamentos", null, null, null, null, null, null)
        val result = mutableListOf<Lancamentos>()

        while (cursor.moveToNext()) {
            result.add(
                Lancamentos(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
                )
            )
        }

        cursor.close()
        return result
    }
}

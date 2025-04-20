package br.edu.utfpr.usandosqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.usandosqlite.entity.Entry

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insert(entry: Entry) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", entry.name)
        values.put("phone", entry.phone)
        db.insert(TABLE_NAME, null, values)
    }

    fun update(entry: Entry) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", entry.name)
        values.put("phone", entry.phone)
        db.update(TABLE_NAME, values, "_id = ${entry._id}", null)
    }

    fun delete(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "_id = $id", null)
    }

    fun search(id: Int): Entry? {
        val db = this.readableDatabase

        val cursor = db.query(TABLE_NAME, null, "_id = ?", arrayOf(id.toString()), null, null, null)

        if (cursor.moveToNext()) {
            val entry = Entry(id, cursor.getString(NAME), cursor.getString(PHONE))

            return entry
        } else return null
    }

    fun list(): Cursor {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        return cursor
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val TABLE_NAME = "record"
        public const val ID = 0
        public const val NAME = 1
        public const val PHONE = 2
    }
}

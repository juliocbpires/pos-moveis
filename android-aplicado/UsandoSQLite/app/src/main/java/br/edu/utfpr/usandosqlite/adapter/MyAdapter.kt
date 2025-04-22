package br.edu.utfpr.usandosqlite.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import br.edu.utfpr.usandosqlite.MainActivity
import br.edu.utfpr.usandosqlite.R
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.entity.Entry

class MyAdapter(var context: Context, var cursor: Cursor): BaseAdapter() {
    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(position: Int): Any {
        cursor.moveToPosition(position)
        val entry = Entry(
            cursor.getInt(DatabaseHandler.ID),
            cursor.getString(DatabaseHandler.NAME),
            cursor.getString(DatabaseHandler.PHONE)
        )
        return entry
    }

    override fun getItemId(position: Int): Long {
        cursor.moveToPosition(position)
        return cursor.getInt(DatabaseHandler.ID).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.element_list, null)

        val tvNameElementList = view.findViewById<TextView>(R.id.tvNameElementList)
        val tvPhoneElementList = view.findViewById<TextView>(R.id.tvPhoneElementList)
        val btEditElementList = view.findViewById<ImageButton>(R.id.btEditElementList)

        cursor.moveToPosition(position)

        tvNameElementList.text = cursor.getString(DatabaseHandler.NAME)
        tvPhoneElementList.text = cursor.getString(DatabaseHandler.PHONE)

        btEditElementList.setOnClickListener {
            cursor.moveToPosition(position)
            val intent = Intent(context, MainActivity::class.java)

            intent.putExtra("id", cursor.getInt(DatabaseHandler.ID))
            intent.putExtra("name", cursor.getString(DatabaseHandler.NAME))
            intent.putExtra("phone", cursor.getString(DatabaseHandler.PHONE))

            context.startActivity(intent)
        }

        return view
    }
}

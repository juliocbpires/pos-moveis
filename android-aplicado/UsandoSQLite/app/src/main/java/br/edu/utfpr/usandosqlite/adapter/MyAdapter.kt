package br.edu.utfpr.usandosqlite.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
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

        cursor.moveToPosition(position)

        tvNameElementList.text = cursor.getString(DatabaseHandler.NAME)
        tvPhoneElementList.text = cursor.getString(DatabaseHandler.PHONE)

        return view
    }
}

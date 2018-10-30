package com.the_akm.akm.tee

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList

class TweeterAdapter( var con: Context,var strings: List<String>) : ArrayAdapter<String>(con, 0, strings) {


    override fun getView(position: Int, convertview: View?, parent: ViewGroup): View {
        var convertView = convertview

        val viewholder: ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(R.layout.card_view, parent, false)
            viewholder = ViewHolder(convertView!!)
            convertView.tag = viewholder
        } else {
            viewholder = convertView.tag as ViewHolder
        }



        val size = strings.size.toString()
        val st = getItem(position)

        if(strings.size == 1){
            viewholder.info_text.text = st
        }
        else {
            val text = (position + 1).toString() + "/" + size + " " + st
            viewholder.info_text.text = text
        }

        return convertView
    }

    inner class ViewHolder internal constructor(row: View) {
        internal var info_text: TextView

        init {
            this.info_text = row.findViewById<View>(R.id.info_text) as TextView
        }

    }
}

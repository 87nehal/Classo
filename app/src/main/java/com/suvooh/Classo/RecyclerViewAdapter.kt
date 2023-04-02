package com.suvooh.Classo

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suvooh.Classo.R

class RecyclerViewAdapter(private val dataList: ArrayList<Pair<String, String>>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val column1TextView: TextView = itemView.findViewById(R.id.nameTextView)
        val column2TextView: TextView = itemView.findViewById(R.id.urlTextView)

        init {
            // Set an OnClickListener to the item view
            column2TextView.setOnClickListener {
                val url = Uri.parse(column2TextView.text.toString())
                val intent = Intent(Intent.ACTION_VIEW, url)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.column1TextView.text = currentItem.first
        holder.column2TextView.text = currentItem.second
    }

    override fun getItemCount() = dataList.size
}

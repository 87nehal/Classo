package com.suvooh.Classo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DictionaryEntryAdapter(var entries: List<DictionaryEntry>) :
    RecyclerView.Adapter<DictionaryEntryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val englishWordTextView: TextView = itemView.findViewById(R.id.EngTextView)
        val romajiWordTextView: TextView = itemView.findViewById(R.id.RomajiTextView)
        val hiraganaWordTextView: TextView = itemView.findViewById(R.id.HiraganTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dictionary_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.englishWordTextView.text = entry.englishWord
        holder.romajiWordTextView.text = entry.romajiWord
        holder.hiraganaWordTextView.text = entry.hiraganaWord
    }

    override fun getItemCount() = entries.size
}



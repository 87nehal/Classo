package com.suvooh.Classo

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suvooh.Classo.databinding.FragmentDictionaryBinding
import com.suvooh.Classo.databinding.FragmentMainBinding

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: DictionaryEntryAdapter

    private val dictionaryWords = DictionaryData().dictionaryWords

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDictionaryBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerView
        searchView = binding.searchView

        adapter = DictionaryEntryAdapter(dictionaryWords)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return true
            }
        })

        return binding.root
    }
    private fun DictionaryEntryAdapter.filter(query: String?) {
        val filteredEntries = if (query.isNullOrBlank()) {
            dictionaryWords
        } else {
            dictionaryWords.filter { entry ->
                entry.englishWord.contains(query, ignoreCase = true) ||
                        entry.romajiWord.contains(query, ignoreCase = true)||
                                entry.hiraganaWord.contains(query, ignoreCase = true)
            }
        }
        entries = filteredEntries
        notifyDataSetChanged()
    }
}
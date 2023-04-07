package com.suvooh.Classo

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.suvooh.Classo.databinding.FragmentHomeworkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.*


class HomeworkFragment : Fragment() {

    private lateinit var binding: FragmentHomeworkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeworkBinding.inflate(layoutInflater)
        val colors = arrayOf(
            Color.parseColor("#4D455D"),
            Color.parseColor("#E96479"),
            Color.parseColor("#7DB9B6"),
            Color.parseColor("#A459D1"),
            Color.parseColor("#E5D1FA"),
            Color.parseColor("#FFD4D4"),
            Color.parseColor("#AACB73"),
            Color.parseColor("#7B2869"),
            Color.parseColor("#FF0000"),
            Color.parseColor("#00FF00"),
            Color.parseColor("#0000FF"),
            Color.parseColor("#9B4E6B"),
            Color.parseColor("#F0A07B"),
            Color.parseColor("#83B5D1"),
            Color.parseColor("#B38D97"),
            Color.parseColor("#87BBA2"),
            Color.parseColor("#AA6E2D"),
            Color.parseColor("#D07C94"),
            Color.parseColor("#4285F4"),
            Color.parseColor("#DB4437"),
            Color.parseColor("#F4B400"),
            Color.parseColor("#0F9D58"),
            Color.parseColor("#8F3E97"),
            Color.parseColor("#FF6F61"),
            Color.parseColor("#6B5B95"),
            Color.parseColor("#F67280"),
            Color.parseColor("#C06C84"),
            Color.parseColor("#355C7D"),
            Color.parseColor("#F5B7B1"),
            Color.parseColor("#99B898"),
            Color.parseColor("#FECE44"),
            Color.parseColor("#5C4B51"),
            Color.parseColor("#B4A8BD"),
            Color.parseColor("#F0EAD6"),
            Color.parseColor("#1ABC9C"),
            Color.parseColor("#2E86C1"),
            Color.parseColor("#3498DB"),
            Color.parseColor("#E74C3C"),
            Color.parseColor("#9B59B6"),
            Color.parseColor("#1E824C"),
            Color.parseColor("#F1A9A0"),
            Color.parseColor("#16A085"),
            Color.parseColor("#27AE60"),
            Color.parseColor("#2980B9"),
            Color.parseColor("#8E44AD"),
            Color.parseColor("#F7DC6F"),
            Color.parseColor("#EB984E"),
            Color.parseColor("#C0392B"),
            Color.parseColor("#BDC3C7"),
            Color.parseColor("#2C3E50"),
            Color.parseColor("#FF6348")
        )


        var colorIndex = 0
        binding.note.setOnClickListener {
            // cycle through the colors array
            colorIndex = (colorIndex + 1) % colors.size

            // create a ColorStateList using the current color in the array
            val rippleColor = ColorStateList.valueOf(colors[colorIndex])

            // set the ripple color of the card view
            binding.note.rippleColor = rippleColor
        }
        binding.homework.setOnClickListener {
            // cycle through the colors array
            colorIndex = (colorIndex + 1) % colors.size

            // create a ColorStateList using the current color in the array
            val rippleColor = ColorStateList.valueOf(colors[colorIndex])

            // set the ripple color of the card view
            binding.homework.rippleColor = rippleColor
        }
        if(CheckInternet().isInternetAvailable(context)) {
            lifecycleScope.launch(Dispatchers.IO) {
                val data = dataGetter()
                withContext(Dispatchers.Main) {
                    binding.homeworkData.text = data[0].second
                    binding.noticeData.text = data[0].first
                }
            }
        }else{
            binding.homeworkData.text = "No Internet"
            binding.noticeData.text = "No Internet"
        }
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun dataGetter(): ArrayList<Pair<String, String>> {
        val data = ArrayList<Pair<String, String>>()
        val sheetUrl = "https://docs.google.com/spreadsheets/d/e/2PACX-1vTLom_ZKs3zeVE1qMupTPlHlwzIoj32Ti_Icu_ktJyYo7l6SMPoRHe-BzJGvsk_K-3r0njNFASccNpi/pubhtml"
        val rawUrl = sheetUrl.replace("/pubhtml", "/pub?output=csv")
        val url = URL(rawUrl)
        url.openStream().bufferedReader().use { reader ->
            reader.useLines { lines ->
                lines.drop(1).forEach { line ->
                    val columns = line.split(",")
                    val column1 = columns[0]
                    val column2 = columns[1]
                    data.add(Pair(column1, column2))
                }
            }
        }
        return data
    }
}
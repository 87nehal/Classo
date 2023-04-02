package com.suvooh.Classo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suvooh.Classo.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        val openURL = Intent(Intent.ACTION_VIEW)

        binding.weekdays.setOnClickListener{
            openURL.data = Uri.parse("https://zoom.us/j/97616749468?pwd=Wkh4TVl1OGhoeC9GQzhMQ0l3UlA2QT09")
            startActivity(openURL)
        }
        binding.weekends.setOnClickListener{
            openURL.data = Uri.parse("https://zoom.us/j/94811719702?pwd=R1hFRzhUUzJDMlJOZHJjN25QQk0xQT09")
            startActivity(openURL)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}
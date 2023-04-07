package com.suvooh.Classo

import DirectoryFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.suvooh.Classo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragement(MainFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.MainFragment -> replaceFragement(MainFragment())
                R.id.DirectoryFragment -> replaceFragement(DirectoryFragment())
                R.id.HomeworkFragment -> replaceFragement(HomeworkFragment())
                R.id.TranslateFragment -> replaceFragement(TranslateFragment())
                R.id.DictionaryFragment -> replaceFragement(DictionaryFragment())
                else ->{

                }
            }
            true
        }
    }
    private fun replaceFragement(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_view,fragment)
        fragmentTransaction.commit()
    }
}
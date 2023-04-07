package com.suvooh.Classo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import com.suvooh.Classo.databinding.FragmentTranslateBinding
import com.suvooh.Classo.Translate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateFragment : Fragment() {

    private lateinit var binding: FragmentTranslateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTranslateBinding.inflate(layoutInflater)

        var srcLang="en"
        var targetLang="ja"
        binding.langSelect.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                targetLang = "en"
                srcLang = "ja"
                binding.input.hint = "ja"
                binding.output.text = "en"
            } else {
                srcLang = "en"
                targetLang = "ja"
                binding.input.hint = "en"
                binding.output.text = "ja"
            }
        }

        if(!CheckInternet().isInternetAvailable(context)) {
            Log.d("No Internet","True")
            binding.input.hint = "No Internet"
            binding.output.text = "No Internet"
            binding.input.isEnabled=false
            binding.langSelect.isEnabled=false
        }

        binding.input.addTextChangedListener {
            var translate: String
            GlobalScope.launch(Dispatchers.Main) {
                // perform network operation in background thread
                val result = withContext(Dispatchers.IO) {
                    translate = Translate().translate(srcLang, targetLang, binding.input.text.toString())
                }
                binding.output.text = translate
            }
        }
        binding.copyoutput.setOnClickListener {
            val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Output", binding.output.text.toString())
            clipboardManager.setPrimaryClip(clipData)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}


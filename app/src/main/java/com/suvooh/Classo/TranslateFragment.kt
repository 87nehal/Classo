package com.suvooh.Classo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.suvooh.Classo.databinding.FragmentTranslateBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TranslateFragment : Fragment(),TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentTranslateBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(requireContext(), this)
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.JAPANESE)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTranslateBinding.inflate(layoutInflater)
        //textToSpeech = TextToSpeech(context, this)

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
            binding.speak.isEnabled=false
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

        binding.speak.setOnClickListener {
            if (::tts.isInitialized) {
                val text = binding.output.text.toString()
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            } else {
                Log.e("TTS", "TextToSpeech engine not initialized")
            }
        }
        
        // Inflate the layout for this fragment
        return binding.root
    }

}


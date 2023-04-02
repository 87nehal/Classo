import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suvooh.Classo.R
import com.suvooh.Classo.RecyclerViewAdapter
import com.suvooh.Classo.databinding.FragmentDirectoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class DirectoryFragment : Fragment() {

    private lateinit var binding: FragmentDirectoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDirectoryBinding.inflate(layoutInflater)

        val imageView = binding.loading
        Glide.with(this).asGif().load(R.drawable.loading).into(imageView)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch(Dispatchers.IO) {
            val data = dataGetter()
            withContext(Dispatchers.Main) {
                val adapter = RecyclerViewAdapter(data)
                recyclerView.adapter = adapter

                binding.loading.visibility = View.GONE
            }
        }

        return binding.root
    }

    private fun dataGetter(): ArrayList<Pair<String, String>> {
        val data = ArrayList<Pair<String, String>>()
        val sheetUrl = "https://docs.google.com/spreadsheets/d/e/2PACX-1vRhlbKXJ4ymhTWifuSDO2XRBgZxuFDZs33YJ0cuWq29bFSLJk_4AzIJ8_j_ssvxQLbco4E_EZuI-eXv/pubhtml"
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

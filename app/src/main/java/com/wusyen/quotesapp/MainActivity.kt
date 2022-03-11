package com.wusyen.quotesapp

import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wusyen.quotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        RequestManager(this@MainActivity).getAllQuotes(listener)
        dialog = ProgressDialog(this@MainActivity)
        dialog?.setTitle("Loading...")
        dialog?.show()
    }

    private val listener: QuotesResponseListener = object : QuotesResponseListener {
        override fun didFetch(response: List<QuotesResponse>, message: String) {
            dialog?.dismiss()
            binding.rvHome.setHasFixedSize(true)
            binding.rvHome.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            val adapter = QuotesListAdapter(this@MainActivity, response, copyListener)
            binding.rvHome.adapter = adapter
        }

        override fun didError(message: String) {
            dialog?.dismiss()
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }

    }

    private val copyListener: CopyListener = object : CopyListener {
        override fun onCopyClicked(text: String) {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("copied_data", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@MainActivity, "Quote Copied to Clipboard", Toast.LENGTH_LONG).show()
        }
    }
}
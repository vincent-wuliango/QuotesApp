package com.wusyen.quotesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wusyen.quotesapp.databinding.ListQuotesBinding

class QuotesListAdapter(
    private val mContext: Context,
    private val quoteList: List<QuotesResponse>,
    private val listener: CopyListener) :
    RecyclerView.Adapter<QuotesListAdapter.QuotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val binding =
            ListQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val quotes: QuotesResponse = quoteList[position]
        holder.bind(quotes, listener)

    }

    override fun getItemCount(): Int = quoteList.size

    class QuotesViewHolder(private val binding: ListQuotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quotes: QuotesResponse, listener: CopyListener) {
            binding.apply {
                binding.tvQuotes.text = quotes.text
                binding.tvAuthor.text = quotes.author
                binding.btnCopy.setOnClickListener {
                    listener.onCopyClicked(quotes.text + "\n" + "By:" + quotes.author)
                }
            }
        }
    }
}
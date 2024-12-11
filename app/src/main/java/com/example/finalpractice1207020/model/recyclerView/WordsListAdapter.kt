package com.example.finalpractice1207020.model.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalpractice1207020.databinding.WordCellBinding
import com.example.finalpractice1207020.model.roomDB.Word

class WordsListAdapter(private val data: MutableList<Word>): RecyclerView.Adapter<WordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        // create the binding from the provided inflater
        val binding = WordCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        // return a new ViewHolder using the inflated binding
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // let ViewHolder handle its own UI updates
        holder.setWord(data[position].word)
    }
}
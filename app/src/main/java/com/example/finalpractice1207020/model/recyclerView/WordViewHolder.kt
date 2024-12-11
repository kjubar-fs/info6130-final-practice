package com.example.finalpractice1207020.model.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.finalpractice1207020.databinding.WordCellBinding

class WordViewHolder(private val binding: WordCellBinding): RecyclerView.ViewHolder(binding.root) {
    /**
     * Set the word displayed by this ViewHolder.
     * @param word word to display
     */
    fun setWord(word: String) {
        binding.tvWord.text = word
    }
}
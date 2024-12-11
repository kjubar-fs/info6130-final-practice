package com.example.finalpractice1207020.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalpractice1207020.model.recyclerView.WordsListAdapter
import com.example.finalpractice1207020.viewModel.WordsViewModel
import com.example.finalpractice1207020.databinding.FragmentListBinding
import com.example.finalpractice1207020.model.roomDB.Word

/**
 * Fragment containing the list of words to display.
 */
class ListFragment : Fragment() {
    /** view binding */
    private lateinit var binding: FragmentListBinding
    /** view model, shared with other fragments and the host activity */
    private val viewModel: WordsViewModel by activityViewModels { WordsViewModel.Factory }

    /** list of words to display, mirrored from the view model to link to RecyclerView adapter */
    private val wordList = mutableListOf<Word>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflate the fragment's view binding
        binding = FragmentListBinding.inflate(inflater, container, false)
        // return the root view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create the adapter from the list of words
        val adapter = WordsListAdapter(wordList)

        // associate adapter and a LayoutManager with the RecyclerView
        binding.rvWordList.adapter = adapter
        binding.rvWordList.layoutManager = LinearLayoutManager(view.context)

        // set up an observer on the view model's word list
        viewModel.wordList.observe(viewLifecycleOwner) { words ->
            // clear our local copy of the word list first
            wordList.clear()

            // update the word list with the new set of words
            wordList.addAll(words)

            // let the adapter know the list changed so it should rerender
            adapter.notifyDataSetChanged()
        }
    }
}
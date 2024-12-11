package com.example.finalpractice1207020.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.finalpractice1207020.viewModel.WordsViewModel
import com.example.finalpractice1207020.databinding.FragmentFormBinding
import com.example.finalpractice1207020.model.roomDB.Word
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

/**
 * Fragment containing the form to add words to the list.
 */
class FormFragment : Fragment() {
    /** view binding */
    private lateinit var binding: FragmentFormBinding
    /** view model, shared with other fragments and the host activity */
    private val viewModel: WordsViewModel by activityViewModels { WordsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflate the fragment's view binding
        binding = FragmentFormBinding.inflate(inflater, container, false)
        // return the root view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddWord.setOnClickListener {
            // get word from input
            val newWord = binding.etNewWord.text.toString()

            // if word isn't blank (whitespace), add it to the list
            if (newWord.isNotBlank()) {
                // launch method scoped to this fragment's lifecycle owner
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.addWord(Word(newWord, Firebase.auth.uid!!))
                }
            }

            // clear the input
            binding.etNewWord.setText("")
        }

        binding.btnRandomWord.setOnClickListener {
            // add a random word from the API to the list
            // launch method scoped to this fragment's lifecycle owner
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.addRandomWord()
            }
        }
    }
}
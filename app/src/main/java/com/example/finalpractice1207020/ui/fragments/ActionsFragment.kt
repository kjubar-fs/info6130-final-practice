package com.example.finalpractice1207020.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.finalpractice1207020.R
import com.example.finalpractice1207020.databinding.FragmentActionsBinding
import com.example.finalpractice1207020.viewModel.WordsViewModel
import kotlinx.coroutines.launch

/**
 * Fragment containing action buttons to clear the word lists.
 */
class ActionsFragment: Fragment() {
    /** view binding */
    private lateinit var binding: FragmentActionsBinding
    /** view model, shared with other fragments and the host activity */
    private val viewModel: WordsViewModel by activityViewModels { WordsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflate the fragment's view binding
        binding = FragmentActionsBinding.inflate(inflater, container, false)
        // return the root view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDeleteMyWords.setOnClickListener {
            // prompt user to delete their words
            AlertDialog.Builder(context)
                .setTitle("Delete Your Words?")
                .setMessage("Are you sure you wish to delete ALL of your words?")
                .setPositiveButton("Delete") { _,_ ->
                    // launch method scoped to this fragment's lifecycle owner
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.deleteUserWords()
                    }
                }
                .setNegativeButton("Cancel") { _,_ ->}
                .show()
        }

        binding.btnDeleteAllWords.setOnClickListener {
            // prompt user to delete all words
            AlertDialog.Builder(context)
                .setTitle("Delete All Words?")
                .setMessage("Are you sure you wish to delete ALL words for ALL users?")
                .setPositiveButton("Delete") { _,_ ->
                    // launch method scoped to this fragment's lifecycle owner
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.deleteAllWords()
                    }
                }
                .setNegativeButton("Cancel") { _,_ ->}
                .show()
        }
    }
}
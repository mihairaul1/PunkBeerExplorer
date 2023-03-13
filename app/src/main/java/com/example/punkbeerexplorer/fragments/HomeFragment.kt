package com.example.punkbeerexplorer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punkbeerexplorer.adapters.BeersAdapter
import com.example.punkbeerexplorer.R
import com.example.punkbeerexplorer.databinding.FragmentHomeBinding
import com.example.punkbeerexplorer.util.Constants.Companion.CURRENT_POSITION_KEY
import com.example.punkbeerexplorer.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var scrollPosition = 0

    private val homeMvvm: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(
            this, HomeViewModel.Factory(activity.application)
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.title = "PunkBeerExplorer"

        binding = FragmentHomeBinding.bind(view)
        linearLayoutManager = LinearLayoutManager(context)
        binding.rvBeersList.layoutManager = linearLayoutManager

        // Restore scroll position if it exists
        savedInstanceState?.let {
            scrollPosition = it.getInt(CURRENT_POSITION_KEY, 0)
        }

        observeBeersList()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the position of the first visible item in the LinearLayoutManager
        scrollPosition = linearLayoutManager.findFirstVisibleItemPosition()
        outState.putInt(CURRENT_POSITION_KEY, scrollPosition)
    }

    private fun observeBeersList() {
        homeMvvm.beersLiveData.observe(
            viewLifecycleOwner
        ) { beersList ->
            binding.rvBeersList.apply {
                val myAdapter = BeersAdapter(beersList)
                binding.rvBeersList.adapter = myAdapter

                // Scroll to the saved position after the RecyclerView is done laying out the items
                post {
                    linearLayoutManager.scrollToPosition(scrollPosition)
                }
            }
        }
    }
}
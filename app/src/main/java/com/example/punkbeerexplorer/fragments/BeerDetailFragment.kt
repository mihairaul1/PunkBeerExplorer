package com.example.punkbeerexplorer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.punkbeerexplorer.R
import com.example.punkbeerexplorer.database.BeersDatabase
import com.example.punkbeerexplorer.database.DatabaseBeer
import com.example.punkbeerexplorer.databinding.FragmentBeerDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BeerDetailFragment : Fragment() {

    private lateinit var binding: FragmentBeerDetailBinding
    private lateinit var beerResponseItem: DatabaseBeer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database: BeersDatabase = BeersDatabase.getDatabase(this.requireContext())

        binding = FragmentBeerDetailBinding.bind(view)

        lifecycleScope.launch {
            arguments?.let {
                withContext(Dispatchers.IO) {
                    val beerId= BeerDetailFragmentArgs.fromBundle(it).beerId
                    beerResponseItem = database.beerDao().getBeerById(beerId)
                }
                withContext(Dispatchers.Main) {
                    Glide.with(view).load(beerResponseItem.image_url).into(binding.ivBeerDetailImage)
                    binding.tvBeerDetailName.text = beerResponseItem.name
                    binding.tvBeerDetailStrength.text = view.context.getString(R.string.abv, beerResponseItem.abv.toString())
                    binding.tvBeerDetailDescription.text = beerResponseItem.description
                    binding.tvBeerDetailFoodPairing.text = buildFoodPairingString(beerResponseItem.food_pairing)
                }
            }
        }
    }

    private fun buildFoodPairingString(arrayList: ArrayList<String>?): String {
        return arrayList?.joinToString(separator = "\n") ?: ""
    }
}
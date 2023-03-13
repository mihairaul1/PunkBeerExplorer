package com.example.punkbeerexplorer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.punkbeerexplorer.R
import com.example.punkbeerexplorer.databinding.BeerItemBinding
import com.example.punkbeerexplorer.fragments.HomeFragmentDirections
import com.example.punkbeerexplorer.models.BeerResponseItem

class BeersAdapter(private val beersList: List<BeerResponseItem>) :
    RecyclerView.Adapter<BeerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        return BeerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.beer_item, parent, false)
        )
    }

    override fun getItemCount() = beersList.size

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bindView(holder.itemView, beersList[position])
    }
}

class BeerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = BeerItemBinding.bind(view)

    fun bindView(view: View, beerItem: BeerResponseItem) {
        binding.ivStrong.visibility = View.GONE
        binding.tvBeerName.text = beerItem.name
        binding.tvBeerStrength.text = view.context.getString(R.string.abv, beerItem.abv.toString())
        binding.tvBeerTagline.text = beerItem.tagline
        Glide.with(itemView).load(beerItem.image_url).into(binding.ivBeerImage)
        if (beerItem.abv > 5) { binding.ivStrong.visibility = View.VISIBLE }
        binding.beerItem.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBeerDetailFragment(beerItem.id)
            Navigation.findNavController(itemView).navigate(action)
        }
    }
}

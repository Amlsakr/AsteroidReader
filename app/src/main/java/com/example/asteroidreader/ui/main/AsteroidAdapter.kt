package com.example.asteroidreader.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidreader.databinding.AsteroidItemBinding
import com.example.asteroidreader.model.Asteroid

class AsteroidAdapter(private val clickListener: AsteroidClickListener)  :
    RecyclerView.Adapter<AsteroidAdapter.ViewHolder>(){

    var asteroidsList = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ViewHolder(private val binding: AsteroidItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(asteroid: Asteroid , itemClickListener: AsteroidClickListener){
            binding.asteroid = asteroid
            binding.clickListener = itemClickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            AsteroidItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(asteroidsList.get(position) , clickListener)
    }

    override fun getItemCount() = asteroidsList.size
}

class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)


}
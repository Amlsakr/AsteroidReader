package com.example.asteroidreader.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.asteroidreader.R
import com.example.asteroidreader.database.AsteroidDB
import com.example.asteroidreader.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
   lateinit var viewModel: MainViewModel
    lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentMainBinding.inflate(inflater)
        val db = AsteroidDB.getDatabase(requireContext())
        viewModel = MainViewModel(db)
        binding.lifecycleOwner = this

        val adapter = AsteroidAdapter(AsteroidClickListener { asteroid ->
            viewModel.onAsteroidClicked(asteroid)
        })

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        with(binding) {
            viewModel = viewModel
            lifecycleOwner = this@MainFragment
            root.asteroid_recycler.adapter = adapter
        }
        setUpObservers(viewModel, adapter)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_menu -> {
                viewModel.getWeekAsteroids()
            }
            R.id.show_today_menu -> {
                viewModel.getTodayAsteroids()
            }
            R.id.show_saved_menu -> {
                viewModel.getSavedAsteroids()
            }
        }
        return true
    }

    private fun setUpObservers(viewModel: MainViewModel, adapter: AsteroidAdapter) {
        with(viewModel) {
            asteroidList.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.asteroidsList = it
                }

                pictureOfDay.observe(viewLifecycleOwner){
                    if (it?.mediaType=="image"){
                        Picasso.get()
                            .load(it.url)
                            .into(binding.activityMainImageOfTheDay);
                        binding.activityMainImageOfTheDay.contentDescription = it.title

                    }
                }
            }
            navigateToAsteroidDetails.observe(viewLifecycleOwner) { asteroid ->
                asteroid?.let {
                    Navigation.findNavController(requireView()).navigate(
                        MainFragmentDirections.actionMainFragmentToDetailFragment(asteroid)
                    )
                    onAsteroidDetailsNavigated()
                }
            }
        }
    }

}
package com.example.udemyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import com.example.udemyapp.R
import com.example.udemyapp.model.DogBreed
import com.example.udemyapp.viewModel.DetailsViewModel
import com.example.udemyapp.viewModel.DetailsViewModelFactory
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.item_dog.*

/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment() {
    private lateinit var viewModel:DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this,DetailsViewModelFactory).get(DetailsViewModel::class.java)
        viewModel.fetch()
        viewModel.dog.observe(this, Observer {dog->
            dog?.let {
                dogName.text=dog.dogBreed
                dogLifespan.text=dog.lifeSpan
            }

        })

    }

}

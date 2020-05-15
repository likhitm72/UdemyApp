package com.example.udemyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.udemyapp.R
import com.example.udemyapp.util.getProgressDrawable
import com.example.udemyapp.util.loadImage
import com.example.udemyapp.viewModel.DetailsViewModel
import com.example.udemyapp.viewModel.DetailsViewModelFactory
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.item_dog.*

/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment() {
    private lateinit var viewModel:DetailsViewModel
    private var dogUUID=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DetailsViewModelFactory.setApplication(activity?.application)
        viewModel=ViewModelProvider(this,DetailsViewModelFactory).get(DetailsViewModel::class.java)


        arguments?.let {
            dogUUID=DetailsFragmentArgs.fromBundle(it).doguuid
        }

        viewModel.fetch(dogUUID)
        viewModel.dog.observe(this, Observer {dog->
            dog?.let {
                dogName.text=dog.dogBreed
                dogPurpose.text=dog.bredFor
                dogTemperament.text=dog.temperament
                dogLifespan.text=dog.lifeSpan
                context?.let {
                    dogImage.loadImage(dog.imageUrl, getProgressDrawable(it))
                }

            }

        })

    }

}

package com.example.udemyapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.udemyapp.R
import com.example.udemyapp.model.DogBreed
import com.example.udemyapp.util.getProgressDrawable
import com.example.udemyapp.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter (val dogsList:ArrayList<DogBreed>):RecyclerView.Adapter<DogListAdapter.DogViewHolder>(){


    class DogViewHolder(var v: View):RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val i=LayoutInflater.from(parent.context)
        val v=i.inflate(R.layout.item_dog,parent,false)
        return DogViewHolder(v)

    }

    override fun getItemCount()=dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.v.name.text=dogsList[position].dogBreed
        holder.v.lifespan.text=dogsList[position].lifeSpan
        dogsList[position].imageUrl?.let { holder.v.imageView.loadImage(it,getProgressDrawable(holder.v.imageView.context)) }
        holder.v.setOnClickListener{
            Navigation.findNavController(it).navigate(ListFragmentDirections.detailsFragmentAction())
        }
    }


    fun updateDogList(newDogsList:List<DogBreed>){
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }


}



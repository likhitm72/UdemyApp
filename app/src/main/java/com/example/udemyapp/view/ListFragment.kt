package com.example.udemyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.udemyapp.R
import com.example.udemyapp.viewModel.ListViewModel
import com.example.udemyapp.viewModel.ListViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var viewModel:ListViewModel
    private var dogListAdapter:DogListAdapter= DogListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ListViewModelFactory.setApplication(activity?.application)
        viewModel=ViewModelProvider(this,ListViewModelFactory).get(ListViewModel::class.java)
        viewModel.refresh()

        dogList.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=dogListAdapter
        }

        refreshLayout.setOnRefreshListener {
            dogList.visibility=View.GONE
            listError.visibility=View.GONE
            loadingView.visibility=View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing=false
        }
        observeViewModel()


    }


    private fun observeViewModel() {

        viewModel.dogs.observe(this, Observer {dogs->
            dogs?.let{listDogs->
                dogList.visibility=View.VISIBLE
                dogListAdapter.updateDogList(listDogs)
            }

        })
        viewModel.dogsLoadError.observe(this, Observer {isError->
            isError?.let {
                listError.visibility=if(it) View.VISIBLE else View.GONE
                if (it){
                    dogList.visibility=View.GONE
                    loadingView.visibility=View.GONE
                }

            }

        })

        viewModel.loading.observe(this, Observer { isLoading->
            isLoading?.let {
                loadingView.visibility=if(it) View.VISIBLE else View.GONE
                if (it){
                    dogList.visibility=View.GONE
                    listError.visibility=View.GONE
                }
            }
        })
    }


}

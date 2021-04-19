package com.example.flickrr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrr.databinding.FragmentSearchBinding
import com.example.flickrr.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class Search : Fragment() {

    private var _binding : FragmentSearchBinding?=null
    private val binding get() = _binding!!
    private lateinit var inputext:String
    private lateinit var viewModel: MainViewModel
    private lateinit var searchrecycler: RecyclerView
    private lateinit var recycleradapter : RecyclerViewNewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
              _binding= FragmentSearchBinding.inflate(layoutInflater,container,false)
               searchrecycler=binding.root.findViewById(R.id.searchrecycler)
               recycleradapter = RecyclerViewNewAdapter(requireContext())
               val repository= Repository()
               val viewModelFactory=MainViewModelFactory(repository)

               viewModel= ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
                searchrecycler.layoutManager = GridLayoutManager(context, 2)
                searchrecycler.adapter = recycleradapter
                  viewModel.scope.launch {
                    viewModel.getDatafromApi().collectLatest {
                    recycleradapter.submitData(it)
            }
        }














        binding.search.setOnClickListener {
            searchrecycler=binding.root.findViewById(R.id.searchrecycler)
            inputext = binding.input.text.toString()
            recycleradapter = RecyclerViewNewAdapter(requireContext())
            val repository= Repository()
            val viewModelFactory=MainViewModelFactory(repository)
            viewModel= ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            searchrecycler.layoutManager = GridLayoutManager(context, 2)
            searchrecycler.adapter = recycleradapter
            viewModel.scope.launch {
                viewModel.getdatawithsearch(inputext).collectLatest {
                    recycleradapter.submitData(it)
                }
            }

        }







        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    }

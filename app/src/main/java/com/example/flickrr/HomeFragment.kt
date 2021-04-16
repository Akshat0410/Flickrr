package com.example.flickrr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.flickrr.Model.Photo
import com.example.flickrr.databinding.FragmentHomeBinding
import com.example.flickrr.repository.Repository

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:RecyclerViewAdapter
    private lateinit var refresher: SwipeRefreshLayout


     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {

         _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
         var dataList:List<Photo>
         recyclerView=binding.galrecycler
         val repository=Repository()
         val viewModelFactory=MainViewModelFactory(repository)
         viewModel=ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
         viewModel.getPost()
         viewModel.MyResponse.observe(viewLifecycleOwner, { response ->
             dataList = response.photos.photo
             recyclerView.layoutManager = GridLayoutManager(context, 2)
             adapter = RecyclerViewAdapter(requireContext(), dataList)
             recyclerView.adapter = adapter
         })

         refresher=binding.refresher
         refresher.setOnRefreshListener{
             onRefresh()
         }

         return binding.root
    }

    private fun onRefresh() {
        refresher.isRefreshing=false
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}






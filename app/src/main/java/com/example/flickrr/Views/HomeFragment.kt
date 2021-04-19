package com.example.flickrr.Views
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrr.*
import com.example.flickrr.Model.Photo
import com.example.flickrr.ViewModel.MainViewModel
import com.example.flickrr.ViewModel.MainViewModelFactory
import com.example.flickrr.databinding.FragmentHomeBinding
import com.example.flickrr.repository.Repository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var mainrecycler: RecyclerView
    private lateinit var recycleradapter : RecyclerViewNewAdapter
    private lateinit var progressbar: ProgressBar
    private lateinit var reload: Button

     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {


         (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home Fragment"
         _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
         mainrecycler = binding.root.findViewById(R.id.galrecycler)
         reload=binding.reloadhome
         progressbar = binding.progressloaderloading
         mainrecycler.layoutManager = GridLayoutManager(context, 2)
         recycleradapter = RecyclerViewNewAdapter(requireContext())
         mainrecycler.adapter = recycleradapter
         val repository = Repository()
         val viewModelFactory = MainViewModelFactory(repository)
         viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
         viewModel.getDatafromApi()

         viewModel.scope.launch {
             progressbar.visibility = View.GONE
             viewModel.getDatafromApi().collectLatest {

                 recycleradapter.submitData(it)

             }

         }

         recycleradapter.addLoadStateListener { loadState ->

                 progressbar.isVisible = loadState.source.refresh is LoadState.Loading
                 reload.setOnClickListener { recycleradapter.refresh() }

         }

         return binding.root

     }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}






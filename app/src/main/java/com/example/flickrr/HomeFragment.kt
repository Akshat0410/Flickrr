package com.example.flickrr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrr.Model.Photo
import com.example.flickrr.databinding.FragmentHomeBinding
import com.example.flickrr.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var dataList: List<Photo>
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var mainrecycler: RecyclerView
    private lateinit var adapter:RecyclerViewAdapter
    private lateinit var recycleradapter : RecyclerViewNewAdapter
//    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var input:EditText

     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {
         (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home Fragment"
         _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
         mainrecycler=binding.root.findViewById(R.id.galrecycler)
         mainrecycler.layoutManager=GridLayoutManager(context,2)
         recycleradapter = RecyclerViewNewAdapter(requireContext())
         mainrecycler.adapter = recycleradapter
         val repository= Repository()
         val viewModelFactory=MainViewModelFactory(repository)
         viewModel= ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
         viewModel.getDatafromApi()
         viewModel.scope.launch {
             viewModel.getDatafromApi().collectLatest {
                 recycleradapter.submitData(it)
             }
         }
         return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}






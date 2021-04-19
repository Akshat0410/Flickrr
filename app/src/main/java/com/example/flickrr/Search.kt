package com.example.flickrr

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.LoadState.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrr.databinding.FragmentSearchBinding
import com.example.flickrr.repository.Repository
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class Search() : Fragment() {

    private var _binding : FragmentSearchBinding?=null
    private val binding get() = _binding!!
    private lateinit var inputext:String
    private lateinit var viewModel: MainViewModel
    private lateinit var searchrecycler: RecyclerView
    private lateinit var recycleradapter : RecyclerViewNewAdapter
    private lateinit var searchView: SearchView
    private val disposable:CompositeDisposable= CompositeDisposable()
    private lateinit var progressbar:ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {

               (requireActivity() as AppCompatActivity).supportActionBar?.title = "Search Fragment"
              _binding= FragmentSearchBinding.inflate(layoutInflater, container, false)
               searchView=binding.searchtext
               progressbar=binding.progressloader
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




        fun debounceSearch(searchView: SearchView) {
            var prevQuery = ""
            val observableQueryText: Observable<String?> = Observable
                .create(ObservableOnSubscribe<String?> { emitter ->
                    searchView.setOnQueryTextListener(object : OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            if (query == prevQuery) return true
                            else prevQuery = query

                            if (!emitter.isDisposed) {
                                emitter.onNext(query)
                            }
                            return true
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            progressbar.visibility = View.VISIBLE
                            if (newText == prevQuery) return true
                            else prevQuery = newText
                            if (!emitter.isDisposed) {
                                emitter.onNext(newText)
                            }

                            return true
                        }
                    })
                })
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())

            observableQueryText.subscribe(object : Observer<String?> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: String) {

                    if (t != null) {
                        GlobalScope.launch(Dispatchers.Main) {
                            progressbar.visibility = View.GONE
                            getSearch(t)

                        }
                    }

                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "gali ho gya ")

                }

                override fun onComplete() {

                }
            })
        }

        debounceSearch(searchView)



        recycleradapter.addLoadStateListener { loadState ->
            binding.apply {
                progressbar.isVisible = loadState.source.refresh is LoadState.Loading
                reload.isVisible = loadState.source.refresh is LoadState.Error



                reload.setOnClickListener{recycleradapter.refresh()}
            }
        }



        return binding.root
    }

    private fun getSearch(t: String) {
            searchrecycler=binding.root.findViewById(R.id.searchrecycler)
             recycleradapter = RecyclerViewNewAdapter(requireContext())
            val repository= Repository()
            val viewModelFactory=MainViewModelFactory(repository)
            viewModel= ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            searchrecycler.layoutManager = GridLayoutManager(context, 2)
            searchrecycler.adapter = recycleradapter
            viewModel.scope.launch {
                viewModel.getdatawithsearch(t).collectLatest {
                    recycleradapter.submitData(it)
                }
            }

        }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }





    }



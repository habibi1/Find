package com.habibi.find.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.habibi.core.data.source.Resource
import com.habibi.find.databinding.SearchFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import android.view.inputmethod.EditorInfo

import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import com.habibi.core.data.source.local.entity.UsersEntity
import com.habibi.find.adapter.CustomAdapter


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private var isFirstTime = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startKeywordObserver()

        binding.edtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.edtSearch.text.toString()
                Log.i("THIIS", "onViewCreated: $query")
                if (query.isNotEmpty())
                    startSearchUsersObserver(query)
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun startKeywordObserver(){
        viewModel.setFirstTimeLoad(true)

        viewModel.firstTimeLoad.observe(viewLifecycleOwner, {
            isFirstTime = it
        })

        viewModel.getKeywordSearch().observe(viewLifecycleOwner, {
            if (isFirstTime)
                if (it.isNullOrBlank())
                    onEmpty()
                else {
                    viewModel.setFirstTimeLoad(false)
                    binding.edtSearch.setText(it)
                    viewModel.setName(it)
                    startSearchUsersObserver(it)
                }
        })
    }

    private fun startSearchUsersObserver(query: String){
        viewModel.getSearchUsers(query).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Empty -> onEmpty()
                is Resource.Error -> onError()
                is Resource.Success -> {
                    viewModel.setName(query)
                    viewModel.saveKeywordSearch(query)
                    setAdapter(it.data!!)
                    onSuccess()
                }
            }
        })
    }

    private fun setAdapter(data: List<UsersEntity>) {
        binding.rvSearch.adapter = CustomAdapter(data){

        }
    }

    private fun onLoading(){
        Log.i("THIIIS", "onLoading: ")
        binding.apply {
            tilSearch.visibility = View.VISIBLE
            edtSearch.isEnabled = false
            rvSearch.visibility = View.GONE
            viewDataEmpty.root.visibility = View.GONE
            viewDataError.root.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun onSuccess(){
        Log.i("THIIIS", "onSuccess: ")
        binding.apply {
            tilSearch.visibility = View.VISIBLE
            edtSearch.isEnabled = true
            rvSearch.visibility = View.VISIBLE
            viewDataEmpty.root.visibility = View.GONE
            viewDataError.root.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun onEmpty(){
        Log.i("THIIS", "onEmpty: ")
        binding.apply {
            tilSearch.visibility = View.VISIBLE
            edtSearch.isEnabled = true
            rvSearch.visibility = View.GONE
            viewDataEmpty.root.visibility = View.VISIBLE
            viewDataError.root.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun onError(){
        Log.i("THIIS", "onError: ")
        binding.apply {
            tilSearch.visibility = View.VISIBLE
            edtSearch.isEnabled = true
            rvSearch.visibility = View.GONE
            viewDataEmpty.root.visibility = View.GONE
            viewDataError.root.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.habibi.find.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.habibi.core.data.source.Resource
import com.habibi.find.databinding.SearchFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import android.view.inputmethod.EditorInfo

import android.widget.TextView.OnEditorActionListener
import androidx.navigation.findNavController
import com.habibi.core.data.source.local.entity.UsersEntity

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private var isFirstTime = true

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

        binding.edtSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.edtSearch.text.toString()
                if (query.isNotEmpty())
                    startSearchUsersObserver(query)
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun startKeywordObserver(){
        viewModel.setFirstTimeLoad(true)

        viewModel.firstTimeLoad.observe(viewLifecycleOwner) {
            isFirstTime = it
        }
        viewModel.getKeywordSearch().observe(viewLifecycleOwner) {
            if (isFirstTime) {
                if (it.isNullOrBlank()) {
                    onEmpty()
                } else {
                    viewModel.setFirstTimeLoad(false)
                    binding.edtSearch.setText(it)
                    viewModel.setName(it)
                    startSearchUsersObserver(it)
                }
            }
        }
    }

    private fun startSearchUsersObserver(query: String){
        viewModel.getSearchUsers(query).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    onLoading()
                }
                is Resource.Empty -> {
                    viewModel.saveKeywordSearch("")
                    onEmpty()
                }
                is Resource.Error -> {
                    onError()
                }
                is Resource.Success -> {
                    setAdapter(it.data!!)
                    onSuccess()
                    viewModel.setName(query)
                    viewModel.saveKeywordSearch(query)
                }
            }
        }
    }

    private fun setAdapter(data: List<UsersEntity>) {
        binding.rvSearch.adapter = CustomAdapter(data){
            val toDetailUserFragment = SearchFragmentDirections.actionSearchFragmentToDetailFragment()
            toDetailUserFragment.login = it.login
            view?.findNavController()?.navigate(toDetailUserFragment)
        }
    }

    private fun onLoading(){
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
        binding.apply {
            tilSearch.visibility = View.VISIBLE
            edtSearch.isEnabled = true
            rvSearch.visibility = View.GONE
            viewDataEmpty.root.visibility = View.GONE
            viewDataError.root.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

        binding.viewDataError.btnTryAgain.setOnClickListener {
            viewModel.refreshLoad()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
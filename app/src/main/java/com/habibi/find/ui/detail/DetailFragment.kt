package com.habibi.find.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.habibi.core.data.source.Resource
import com.habibi.core.data.source.remote.response.DetailUserResponse
import com.habibi.core.data.source.remote.response.UserRepositoryResponseItem
import com.habibi.find.databinding.DetailFragmentBinding
import com.habibi.find.utils.setImage
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModel()

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private var holderDetailLoaded = false
    private var holderRepositoryLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = DetailFragmentArgs.fromBundle(arguments as Bundle).login

        viewModel.getDetailUser(login).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    holderDetailLoaded = false
                    onProfileLoading()
                }
                is Resource.Success -> {
                    holderDetailLoaded = true
                    onProfileSuccess(it.data!!)
                }
                else -> {
                    holderDetailLoaded = true
                    onProfileError()
                }
            }
            hideProgressBar()
        })

        viewModel.getUserRepository(login).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    holderRepositoryLoaded = false
                    onRepositoryLoading()
                }
                is Resource.Empty -> {
                    holderRepositoryLoaded = true
                    onRepositoryEmpty()
                }
                is Resource.Success -> {
                    holderRepositoryLoaded = true
                    onRepositorySuccess(it.data)
                }
                else -> {
                    holderRepositoryLoaded = true
                    onRepositoryError()
                }
            }
            hideProgressBar()
        })
    }

    private fun onProfileLoading(){
        binding.apply {
            ivAvatarDetail.visibility = View.GONE
            tvName.visibility = View.GONE
            tvType.visibility = View.GONE
            tvBio.visibility = View.GONE
            tvFollower.visibility = View.GONE
            tvLocation.visibility = View.GONE
            tvEmail.visibility = View.GONE
            viewDetailError.root.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onProfileSuccess(data: DetailUserResponse){

        binding.apply {
            viewDetailError.root.visibility = View.GONE
            setImage(ivAvatarDetail, data.avatarUrl)
            ivAvatarDetail.visibility = View.VISIBLE

            tvName.visibility = View.VISIBLE
            tvName.text = data.login

            tvType.visibility = View.VISIBLE
            tvType.text = "(${data.type})"

            tvBio.visibility = View.VISIBLE
            tvBio.text =
                if (data.bio.isNullOrEmpty())
                    "-"
                else
                    data.bio

            tvFollower.visibility = View.VISIBLE
            tvFollower.text = "${data.followers} Followers - ${data.following} Following"

            tvLocation.visibility = View.VISIBLE
            tvLocation.text =
                if (data.location.isNullOrEmpty())
                    "-"
                else
                    data.location

            tvEmail.visibility = View.VISIBLE
            tvEmail.text =
                if (data.email.isNullOrEmpty())
                    "-"
                else
                    data.email
        }
    }

    private fun onProfileError(){
        binding.apply {
            ivAvatarDetail.visibility = View.GONE
            tvName.visibility = View.GONE
            tvType.visibility = View.GONE
            tvBio.visibility = View.GONE
            tvFollower.visibility = View.GONE
            tvLocation.visibility = View.GONE
            tvEmail.visibility = View.GONE
            viewDetailError.root.visibility = View.VISIBLE
        }

        binding.viewDetailError.btnTryAgain.setOnClickListener {
            viewModel.refreshDetail()
        }
    }

    private fun onRepositoryLoading(){
        binding.apply {
            viewRepositoryEmpty.root.visibility = View.GONE
            viewRepositoryError.root.visibility = View.GONE
            rvRepository.visibility = View.GONE
        }
    }

    private fun onRepositorySuccess(data: List<UserRepositoryResponseItem?>?){
        binding.rvRepository.adapter = UserRepositoryAdapter(data!!)

        binding.apply {
            viewRepositoryEmpty.root.visibility = View.GONE
            viewRepositoryError.root.visibility = View.GONE
            rvRepository.visibility = View.VISIBLE
        }
    }

    private fun onRepositoryEmpty(){
        binding.apply {
            viewRepositoryEmpty.root.visibility = View.VISIBLE
            viewRepositoryError.root.visibility = View.GONE
            rvRepository.visibility = View.GONE
        }
    }

    private fun onRepositoryError(){
        binding.apply {
            viewRepositoryEmpty.root.visibility = View.GONE
            viewRepositoryError.root.visibility = View.VISIBLE
            rvRepository.visibility = View.GONE
        }

        binding.viewRepositoryError.btnTryAgain.setOnClickListener {
            viewModel.refreshRepository()
        }
    }

    private fun hideProgressBar(){
        binding.progressBarDetail.visibility =
        if (holderDetailLoaded && holderRepositoryLoaded)
            View.GONE
        else
            View.VISIBLE
    }
}
package com.example.androidsubmision1.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsubmision1.ViewModel.DetailViewModel
import com.example.androidsubmision1.GithubAdapter
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmision1.databinding.FragmentFollowBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.viewmodel.ext.android.viewModel


class FollowFragment(private val username : String) : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel:DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var position = 0

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            detailViewModel.username = it.getString(ARG_USERNAME)
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        runBlocking {
            launch(Dispatchers.IO) {
                detailViewModel.getFollow(username)
            }
        }

        if (position == 1) {
            detailViewModel.followersUser.observe(viewLifecycleOwner) { data ->
                setDataUser(data)
            }
        } else {
            detailViewModel.followingUser.observe(viewLifecycleOwner) { data ->
                setDataUser(data)
            }
        }

    }

    private fun setDataUser(dataUser: List<ItemsItemModel>) {
        val rcFollow = ArrayList<ItemsItemModel>()

        rcFollow.addAll(dataUser)

        val adapter = GithubAdapter(rcFollow, requireActivity())
        binding.rvFollow.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_POSITION = "getPosition"
        const val ARG_USERNAME = "getUsername"
    }
}

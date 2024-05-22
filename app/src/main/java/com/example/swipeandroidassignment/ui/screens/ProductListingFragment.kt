package com.example.swipeandroidassignment.ui.screens

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajit.swipeandroidassignment.utils.toast
import com.example.swipeandroidassignment.R
import com.example.swipeandroidassignment.ui.adapters.ProductListAdapter
import com.example.swipeandroidassignment.ui.viewmodels.ProductViewModel
import com.example.swipeandroidassignment.utils.UiState
import com.example.swipeandroidassignment.databinding.FragmentProductListingBinding
import org.koin.android.ext.android.inject
import java.util.*

class ProductListingFragment : Fragment() {


    private lateinit var binding: FragmentProductListingBinding
    private val viewModel by inject<ProductViewModel>()
    private lateinit var searchView: SearchView
    private lateinit var productAdapter: ProductListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductListingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.window?.let { window ->
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.temp2)
            }
        }

        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        binding.noProductFound.visibility = View.GONE

    }




    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            productAdapter = ProductListAdapter()
            adapter = productAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.productListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    binding.noProductFound.visibility = View.GONE

                    binding.progressBar.visibility = View.GONE
                    productAdapter.updateList(state.data)
                }
                is UiState.Failure -> {
                    binding.noProductFound.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    toast(state.error)

                }
            }
        }
    }



    private fun filterList(query: String?) {
        val deviceListState = viewModel.productListState.value
        if (query != null && deviceListState is UiState.Success) {
            val deviceList = deviceListState.data
            val filteredList = deviceList.filter { item ->
                item.product_name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) ||
                        item.product_type.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) ||
                        item.tax.toString().contains(query) ||
                        item.price.toString().contains(query)
            }

            if (filteredList.isEmpty()) {
                binding.noProductFound.visibility = View.VISIBLE
                productAdapter.updateList(mutableListOf())
                toast("No Data found")
            } else {
                binding.noProductFound.visibility = View.GONE
                productAdapter.updateList(filteredList.toMutableList())
            }
        } else {
            binding.noProductFound.visibility = View.GONE
            productAdapter.updateList(mutableListOf())
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}
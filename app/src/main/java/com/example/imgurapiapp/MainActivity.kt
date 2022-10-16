package com.example.imgurapiapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imgurapiapp.adapter.ImageAdapter
import com.example.imgurapiapp.common.afterTextChangedDebounce
import com.example.imgurapiapp.common.gone
import com.example.imgurapiapp.common.visible
import com.example.imgurapiapp.databinding.ActivityMainBinding
import com.example.imgurapiapp.model.ImgurData
import com.example.imgurapiapp.utils.ViewBindingActivity
import com.example.imgurapiapp.viewmodel.ImgurViewModel
import com.supply.blusip.com.data.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ViewBindingActivity<ActivityMainBinding>() {

    private val imgurViewModel: ImgurViewModel by viewModels()
    private lateinit var imageAdapter: ImageAdapter
    private var imgurData: ArrayList<ImgurData>? = null
    private val sort: String = "time"
    private val window: String = "week"
    private val section: String = "top"
    private var page: Int = 1
    private var currentPosition: Int = 0
    private var isApiCallInProgress = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpRecyclerView()
        getImages()

        binding.appBar.switchGride.setOnCheckedChangeListener { _, isChecked ->
            binding.mainRecyclerView.apply {
                if(isChecked) {
                    layoutManager = GridLayoutManager(this@MainActivity, 3)
                } else{
                    layoutManager = LinearLayoutManager(this@MainActivity)
                }
                if ((imgurData?.size ?: 0) > 0) {
                    imageAdapter.submitList(imgurData)
                    if (imageAdapter.itemCount >= currentPosition) {
                        scrollToPosition(currentPosition)
                    }

                }
            }

        }
        binding.searchText.afterTextChangedDebounce {
            page = 1
            if (it.isNotEmpty())
                searchForImage()
            else
                getImages()
        }

    }

    private fun showLoading() {
        binding.progressBar.progressDialog.visible()
    }

    private fun hideLoading() {
        binding.progressBar.progressDialog.gone()
    }


    private fun setUpRecyclerView() {
        showLoading()
        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            imageAdapter = ImageAdapter()
            adapter = imageAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastPos = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    currentPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (lastPos > imageAdapter.currentList.size / 2 && !isApiCallInProgress
                    ) {
                        page++
                        if (binding.searchText.text.toString().isNotEmpty()) {
                            searchForImage()
                        }else {
                            getImages()
                        }
                    }
                }
            })
        }
    }

    private fun searchForImage() {
        imgurViewModel.searchImages(sort, window, page, binding.searchText.text.toString()).observe(this
        ) {
            when (it) {
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Success -> {
                    if (it.data?.status == 200) {
                        hideLoading()
                        if (page == 1)
                            imgurData = it.data.data
                        else
                            imgurData?.addAll(it.data.data)
                        imageAdapter.submitList(imgurData)
                        Log.d("Main", "searchForImage: " + it.data.toString())
                    } else {
                        Toast.makeText(this, "failed to load", Toast.LENGTH_LONG).show()
                    }
                    hideUnHideEmptyData()
                }

                is Result.Failure -> {
                    hideUnHideEmptyData()
                    hideLoading()
                }
                else -> {
                    hideUnHideEmptyData()
                }
            }
        }
    }

    private fun getImages() {
        imgurViewModel.getImages(section, sort, window, page).observe(this
        ) {
            when (it) {
                is Result.Loading -> {
                   showLoading()
                    isApiCallInProgress = true

                }
                is Result.Success -> {
                    if (it.data?.status == 200) {
                        isApiCallInProgress = false
                        hideLoading()
                        if (page == 1) {
                            imgurData = it.data.data
                        } else {
                            imgurData?.addAll(it.data.data)
                        }
                        imageAdapter.submitList(imgurData)
                    }
                    hideUnHideEmptyData()
                }
                is Result.Failure -> {
                    hideUnHideEmptyData()
                    hideLoading()
                }
                else -> {
                    hideUnHideEmptyData()
                }
            }
        }
    }

    private fun hideUnHideEmptyData() {
        if ((imgurData?.size ?: 0) == 0) {
            binding.noDataFoundText.visible()
            binding.mainRecyclerView.gone()
        } else {
            binding.noDataFoundText.gone()
            binding.mainRecyclerView.visible()
        }
    }


    override fun provideBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }
}
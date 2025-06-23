package com.example.dynamiccarouselapp.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dynamiccarouselapp.R
import com.example.dynamiccarouselapp.presentation.adapter.CarouselAdapter
import com.example.dynamiccarouselapp.presentation.adapter.ItemAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.example.dynamiccarouselapp.databinding.HomeActivityBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeActivityBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupSearch()
        setupCarousel()
        setupItemList()
        observeViewModel()
        setupFab()
    }

    private fun setupSearch() = with(binding) {
        searchEditText.addTextChangedListener { viewModel.onSearchQueryChanged(it.toString()) }
    }

    private fun setupCarousel() = with(binding) {
        carouselAdapter = CarouselAdapter(viewModel.categoryPages.map { it.baseImages })
        viewPager.adapter = carouselAdapter
        viewPager.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onPageChanged(position)
                viewPager.post {
                    updateIndicators(viewModel.currentPageIndex.value ?: 0)
                }
            }
        })
        updateIndicators(0)
    }

    private fun updateIndicators(currentIndex: Int) = with(binding.indicatorLayout) {
        removeAllViews()
        viewModel.categoryPages.indices.forEach { index ->
            val dot = View(this@HomeActivity).apply {
                layoutParams = LinearLayout.LayoutParams(20, 20).apply {
                    setMargins(8, 0, 8, 0)
                }
                setBackgroundResource(
                    if (index == currentIndex) R.drawable.dot_selected else R.drawable.dot_unselected
                )
            }
            addView(dot)
        }
    }

    private fun setupItemList() = with(binding.itemRecyclerView) {
        itemAdapter = ItemAdapter()
        layoutManager = LinearLayoutManager(this@HomeActivity)
        adapter = itemAdapter
    }

    private fun observeViewModel() {
        viewModel.filteredItemList.observe(this) { itemAdapter.updateList(it) }
    }

    private fun setupFab() = with(binding.statsFab) {
        setOnClickListener { showStatisticsBottomSheet() }
    }

    private fun showStatisticsBottomSheet() {
        BottomSheetDialog(this).apply {
            val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
            bottomSheetView.findViewById<TextView>(R.id.statsTextView).text =
                viewModel.getStatisticsText()
            setContentView(bottomSheetView)
            show()
        }
    }
}




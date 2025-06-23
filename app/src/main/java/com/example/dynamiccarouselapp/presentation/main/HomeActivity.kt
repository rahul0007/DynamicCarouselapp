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
import com.example.dynamiccarouselapp.databinding.HomeActivityBinding
import com.example.dynamiccarouselapp.presentation.adapter.CarouselAdapter
import com.example.dynamiccarouselapp.presentation.adapter.ItemAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeActivity : AppCompatActivity() {

    // --- View binding and adapters ---

    private lateinit var binding: HomeActivityBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the view using ViewBinding
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Setup UI components
        setupSearch()
        setupCarousel()
        setupItemList()
        observeViewModel()
        setupFab()
    }

    /**
     * Setup search bar listener to update query in ViewModel
     */
    private fun setupSearch() = with(binding) {
        searchEditText.addTextChangedListener {
            viewModel.onSearchQueryChanged(it.toString())
        }
    }

    /**
     * Setup ViewPager carousel with indicator dots
     */
    private fun setupCarousel() = with(binding) {
        // Initialize adapter with base images
        carouselAdapter = CarouselAdapter(viewModel.categoryPages.map { it.baseImages })
        viewPager.adapter = carouselAdapter

        // Handle page swipe events
        viewPager.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onPageChanged(position)

                // Delay updating indicators to ensure layout is ready
                viewPager.post {
                    updateIndicators(viewModel.currentPageIndex.value ?: 0)
                }
            }
        })

        // Initial indicator update
        updateIndicators(0)
    }

    /**
     * Update dot indicators based on current page
     */
    private fun updateIndicators(currentIndex: Int) = with(binding.indicatorLayout) {
        removeAllViews()
        viewModel.categoryPages.indices.forEach { index ->
            val dot = View(this@HomeActivity).apply {
                layoutParams = LinearLayout.LayoutParams(20, 20).apply {
                    setMargins(8, 0, 8, 0)
                }
                setBackgroundResource(
                    if (index == currentIndex)
                        R.drawable.dot_selected
                    else
                        R.drawable.dot_unselected
                )
            }
            addView(dot)
        }
    }

    /**
     * Setup RecyclerView to show items in current category
     */
    private fun setupItemList() = with(binding.itemRecyclerView) {
        itemAdapter = ItemAdapter()
        layoutManager = LinearLayoutManager(this@HomeActivity)
        adapter = itemAdapter
    }

    /**
     * Observe filtered item list and update adapter
     */
    private fun observeViewModel() {
        viewModel.filteredItemList.observe(this) { filteredItems ->
            itemAdapter.updateList(filteredItems)
        }
    }

    /**
     * Setup FloatingActionButton to show bottom sheet
     */
    private fun setupFab() = with(binding.statsFab) {
        setOnClickListener {
            showStatisticsBottomSheet()
        }
    }

    /**
     * Display bottom sheet with statistics from ViewModel
     */
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

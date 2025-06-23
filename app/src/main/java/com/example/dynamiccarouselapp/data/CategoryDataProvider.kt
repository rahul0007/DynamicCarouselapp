package com.example.dynamiccarouselapp.data
import com.example.dynamiccarouselapp.R
import com.example.dynamiccarouselapp.domain.model.CategoryPage
import com.example.dynamiccarouselapp.domain.model.ListItemData

object CategoryDataProvider {
    fun getAllCategories(): List<CategoryPage> = listOf(
        CategoryPage(
            title = "Fruits",
            baseImages = R.drawable.fruits,
            items = listOf(
                ListItemData("Apple", "Fresh and juicy", R.drawable.apple),
                ListItemData("Banana", "Rich in potassium", R.drawable.banana),
                ListItemData("Orange", "Full of Vitamin C", R.drawable.orange),
                ListItemData("Pineapple", "Tropical and tangy", R.drawable.pineapple),
                ListItemData("Grapes", "Sweet and seedless", R.drawable.grapes),
                ListItemData("Mango", "King of fruits", R.drawable.mango),
                ListItemData("Watermelon", "Refreshing and hydrating", R.drawable.watermelon),
                ListItemData("Papaya", "Soft and nutritious", R.drawable.papaya),
                ListItemData("Guava", "Rich in Vitamin C", R.drawable.guava),
                ListItemData("Lychee", "Sweet and aromatic", R.drawable.lychee),
                ListItemData("Pomegranate", "Juicy with seeds", R.drawable.pomegranate)
            )
        ),
        CategoryPage(
            title = "Vegetables",
            baseImages = R.drawable.vegetables,
            items = listOf(
                ListItemData("Carrot", "Good for eyes", R.drawable.carrot),
                ListItemData("Tomato", "Rich in lycopene", R.drawable.tomato),
                ListItemData("Spinach", "Iron rich", R.drawable.spinach),
                ListItemData("Broccoli", "High in fiber", R.drawable.broccoli),
                ListItemData("Potato", "Starchy and filling", R.drawable.potato),
                ListItemData("Onion", "Essential in cooking", R.drawable.onion),
                ListItemData("Cucumber", "Cool and crunchy", R.drawable.cucumber),
                ListItemData("Peas", "Green and sweet", R.drawable.peas)
            )
        ),
        CategoryPage(
            title = "Berries",
            baseImages = R.drawable.berries,
            items = listOf(
                ListItemData("Strawberry", "Sweet and red", R.drawable.strawberry),
                ListItemData("Blueberry", "Antioxidant-rich", R.drawable.blueberry),
                ListItemData("Raspberry", "Tart and tasty", R.drawable.raspberry)
            )
        )
    )
}

package com.example.dynamiccarouselapp
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import com.example.dynamiccarouselapp.presentation.main.HomeScreen
import com.example.dynamiccarouselapp.ui.theme.ContainerColor
import com.example.dynamiccarouselapp.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = ContainerColor
                ) {
                    HomeScreen()
                }
            }
        }
    }
}
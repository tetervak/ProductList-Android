package ca.tetervak.productlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ca.tetervak.productlist.ui.AppRootScreen
import ca.tetervak.productlist.ui.theme.ProductListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductListTheme {
                AppRootScreen()
            }
        }
    }
}

package ca.tetervak.productlist.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ca.tetervak.productlist.ui.navigation.ProductListNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppRootScreen(navController: NavHostController = rememberNavController()) {
    ProductListNavHost(navController = navController)
}


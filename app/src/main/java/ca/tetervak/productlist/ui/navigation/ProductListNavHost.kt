package ca.tetervak.productlist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ca.tetervak.productlist.ui.product.list.ProductListScreen
import ca.tetervak.productlist.ui.product.list.ProductListViewModel
import ca.tetervak.productlist.ui.product.details.ProductDetailsScreen
import ca.tetervak.productlist.ui.product.details.ProductDetailsViewModel
import ca.tetervak.productlist.ui.product.edit.ProductEditScreen
import ca.tetervak.productlist.ui.product.edit.ProductEditViewModel
import ca.tetervak.productlist.ui.product.entry.ProductEntryScreen
import ca.tetervak.productlist.ui.product.entry.ProductEntryViewModel

@Composable
fun ProductListNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ProductListDestination.route,
        modifier = modifier
    ) {
        composable(route = ProductListDestination.route) {
            val viewModel: ProductListViewModel = hiltViewModel()
            ProductListScreen(
                navigateToProductEntry = { navController.navigate(ProductEntryDestination.route) },
                navigateToProductDetails = { id->
                    navController.navigate("${ProductDetailsDestination.route}/${id}")
                },
                viewModel = viewModel
            )
        }
        composable(route = ProductEntryDestination.route) {
            val viewModel: ProductEntryViewModel = hiltViewModel()
            ProductEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.PRODUCT_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            val viewModel: ProductDetailsViewModel = hiltViewModel()
            ProductDetailsScreen(
                navigateToEditProduct = { navController.navigate("${ProductEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
        composable(
            route = ProductEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductEditDestination.PRODUCT_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            val viewModel: ProductEditViewModel = hiltViewModel()
            ProductEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
    }
}

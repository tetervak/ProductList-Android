package ca.tetervak.productlist.ui.navigation

import ca.tetervak.productlist.R

object ProductDetailsDestination : NavigationDestination {
    override val route = "product-details"
    override val titleRes = R.string.product_details_title
    const val PRODUCT_ID_ARG = "productId"
    val routeWithArgs = "$route/{$PRODUCT_ID_ARG}"
}
package ca.tetervak.productlist.ui.navigation

import ca.tetervak.productlist.R

object ProductEditDestination : NavigationDestination {
    override val route = "product_edit"
    override val titleRes = R.string.edit_product_title
    const val PRODUCT_ID_ARG = "productId"
    val routeWithArgs = "$route/{$PRODUCT_ID_ARG}"
}
package ca.tetervak.productlist.ui.product.details

import ca.tetervak.productlist.domain.Product
import ca.tetervak.productlist.ui.product.model.ProductDetailsModel
import ca.tetervak.productlist.ui.product.model.toProductDetailsModel

/**
 * UI state for ItemDetailsScreen
 */
data class ProductDetailsUiState(
    val outOfStock: Boolean,
    val detailsModel: ProductDetailsModel
){
    constructor(product: Product): this (
        outOfStock = product.quantity <= 0,
        detailsModel = product.toProductDetailsModel()
    )

    constructor(): this(Product())
}
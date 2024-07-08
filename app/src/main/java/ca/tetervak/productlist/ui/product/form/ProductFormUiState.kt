package ca.tetervak.productlist.ui.product.form

import ca.tetervak.productlist.domain.Product
import ca.tetervak.productlist.ui.product.model.ProductFormModel
import ca.tetervak.productlist.ui.product.model.toProductFormModel

data class ProductFormUiState(
    val productFormModel: ProductFormModel = ProductFormModel(),
    val isEntryValid: Boolean = false
)

fun Product.toProductFormUiState(isEntryValid: Boolean = false): ProductFormUiState =
    ProductFormUiState(
        productFormModel = this.toProductFormModel(),
        isEntryValid = isEntryValid
    )
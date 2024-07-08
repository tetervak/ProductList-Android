package ca.tetervak.productlist.ui.product.model

import ca.tetervak.productlist.domain.Condition
import ca.tetervak.productlist.domain.Product
import ca.tetervak.productlist.ui.common.formatCurrency
import ca.tetervak.productlist.ui.common.formatDateTime
import java.util.Date

data class ProductDetailsModel(
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val condition: Condition,
    val rating: Float,
    val date: Date
){
    constructor(product: Product): this(
        id = product.id,
        name = product.name,
        price = formatCurrency(product.price),
        quantity = product.quantity,
        condition = product.condition,
        rating = product.rating,
        date = product.date
    )
}

fun Product.toProductDetailsModel() = ProductDetailsModel(this)



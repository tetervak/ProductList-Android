package ca.tetervak.productlist.ui.product.model

import ca.tetervak.productlist.domain.Condition
import ca.tetervak.productlist.domain.Product
import ca.tetervak.productlist.ui.common.conditionToString
import ca.tetervak.productlist.ui.common.formatCurrency
import ca.tetervak.productlist.ui.common.formatDateTime
import java.util.Date

data class ProductListItemModel(
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val selected: Boolean,
    val condition: Condition,
    val rating: Float = 0.0F,
    val date: String
){
    constructor(product: Product): this(
        id = product.id,
        name = product.name,
        price = formatCurrency(product.price),
        quantity = product.quantity,
        selected = product.selected,
        condition = product.condition,
        rating = product.rating,
        date = formatDateTime(product.date)
    )
}

fun Product.toProductListItemModel() = ProductListItemModel(this)



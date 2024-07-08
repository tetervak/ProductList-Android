package ca.tetervak.productlist.ui.product.model
import ca.tetervak.productlist.domain.Condition
import ca.tetervak.productlist.domain.Product
import java.util.Date

data class ProductFormModel(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val condition: Condition = Condition.NEW,
    val rating: Float = 0.0F,
    val date: Date = Date()
) {
    fun isValid(): Boolean =
        name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()

    fun toProduct(): Product = Product(
        id = id,
        name = name,
        price = price.toDoubleOrNull() ?: 0.0,
        quantity = quantity.toIntOrNull() ?: 0,
        condition = condition,
        rating = rating,
        date = date
    )
}

fun Product.toProductFormModel(): ProductFormModel = ProductFormModel(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString(),
    condition = condition,
    rating = rating,
    date = date
)

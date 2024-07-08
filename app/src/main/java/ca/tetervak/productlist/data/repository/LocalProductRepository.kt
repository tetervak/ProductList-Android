package ca.tetervak.productlist.data.repository

import ca.tetervak.productlist.domain.Product
import ca.tetervak.productlist.data.local.ProductDao
import ca.tetervak.productlist.data.local.LocalProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
class LocalProductRepository(
    private val productDao: ProductDao,
    private val externalScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) : ProductRepository {

    constructor(productDao: ProductDao) : this(productDao, GlobalScope, Dispatchers.IO)

    override fun getAllProductsStream(): Flow<List<Product>> =
        productDao.getAllProductsStream()
            .map{ list -> list.map { localItem ->  localItem.toProduct() }}
            .flowOn(dispatcher)

    override fun getProductByIdStream(id: Int): Flow<Product?> =
        productDao.getProductByIdStream(id)
            .map { localItem -> localItem?.toProduct() }
            .flowOn(dispatcher)

    override suspend fun insertProduct(product: Product) {
        externalScope.launch(dispatcher) { productDao.insertProduct(product.toLocalProduct()) }.join()
    }

    override suspend fun deleteProduct(product: Product) {
        externalScope.launch(dispatcher) { productDao.deleteProduct(product.toLocalProduct()) }.join()
    }

    override suspend fun deleteProductById(id: Int){
        externalScope.launch(dispatcher) { productDao.deleteProductById(id) }.join()
    }

    override suspend fun updateProduct(product: Product) {
        externalScope.launch(dispatcher) { productDao.updateProduct(product.toLocalProduct()) }.join()
    }

    override suspend fun updateProductQuantityById(id: Int, quantity: Int) {
        externalScope.launch(dispatcher) { productDao.updateProductQuantityById(id, quantity) }.join()
    }

    override suspend fun updateProductSelectedById(id: Int, selected: Boolean) {
        externalScope.launch(dispatcher) { productDao.updateProductSelectedById(id, selected) }.join()
    }
}

fun LocalProduct.toProduct(): Product = Product(
    id = this.id,
    name = this.name,
    price = this.price,
    quantity = this.quantity,
    selected = this.selected,
    condition = this.condition,
    rating = this.rating,
    date = this.date
)

fun Product.toLocalProduct(): LocalProduct = LocalProduct(
    id = this.id,
    name = this.name,
    price = this.price,
    quantity = this.quantity,
    selected = this.selected,
    condition = this.condition,
    rating = this.rating,
    date = this.date
)

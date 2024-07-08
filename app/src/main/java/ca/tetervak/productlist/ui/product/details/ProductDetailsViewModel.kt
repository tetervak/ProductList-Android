package ca.tetervak.productlist.ui.product.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.productlist.data.repository.ProductRepository
import ca.tetervak.productlist.ui.navigation.ProductDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle[ProductDetailsDestination.PRODUCT_ID_ARG])

    /**
     * Holds the item details ui state. The data is retrieved from [ProductRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<ProductDetailsUiState> =
        productRepository.getProductByIdStream(productId)
            .filterNotNull()
            .map { item ->
                ProductDetailsUiState(item)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ProductDetailsUiState()
            )

    /**
     * Reduces the item quantity by one and update the [ProductRepository]'s data source.
     */
    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val detailsModel = uiState.value.detailsModel
            if (detailsModel.quantity > 0) {
                productRepository.updateProductQuantityById(detailsModel.id, detailsModel.quantity - 1)
            }
        }
    }

    /**
     * Deletes the item from the [ProductRepository]'s data source.
     */
    fun deleteProduct() = viewModelScope.launch{
        productRepository.deleteProductById(uiState.value.detailsModel.id)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


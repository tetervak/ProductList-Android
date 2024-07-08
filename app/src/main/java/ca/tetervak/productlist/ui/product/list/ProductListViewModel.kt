package ca.tetervak.productlist.ui.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.productlist.data.repository.ProductRepository
import ca.tetervak.productlist.ui.product.model.ProductListItemModel
import ca.tetervak.productlist.ui.product.model.toProductListItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val productListUiState: StateFlow<ProductListUiState> =
        productRepository.getAllProductsStream()
            .map { list -> ProductListUiState(list.map { product -> product.toProductListItemModel() }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ProductListUiState()
            )

    fun toggleSelect(listItemModel: ProductListItemModel) {
        viewModelScope.launch {
                productRepository.updateProductSelectedById(listItemModel.id, !listItemModel.selected)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


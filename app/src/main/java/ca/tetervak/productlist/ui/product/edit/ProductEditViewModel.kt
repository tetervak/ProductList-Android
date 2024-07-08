package ca.tetervak.productlist.ui.product.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import ca.tetervak.productlist.data.repository.ProductRepository
import ca.tetervak.productlist.ui.product.form.ProductFormViewModel
import ca.tetervak.productlist.ui.product.form.toProductFormUiState
import ca.tetervak.productlist.ui.product.model.ProductFormModel
import ca.tetervak.productlist.ui.navigation.ProductEditDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
) : ProductFormViewModel() {

    private val productId: Int =
        checkNotNull(savedStateHandle[ProductEditDestination.PRODUCT_ID_ARG])

    init {
        viewModelScope.launch {
            uiState = productRepository.getProductByIdStream(productId)
                .filterNotNull()
                .first()
                .toProductFormUiState(isEntryValid = true)
        }
    }

    fun updateProduct() = viewModelScope.launch {
        val formModel: ProductFormModel = uiState.productFormModel
        if (formModel.isValid()) {
            productRepository.updateProduct(formModel.toProduct())
        }
    }
}

package ca.tetervak.productlist.ui.product.entry

import androidx.lifecycle.viewModelScope
import ca.tetervak.productlist.data.repository.ProductRepository
import ca.tetervak.productlist.ui.product.form.ProductFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductEntryViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ProductFormViewModel() {

    fun saveProduct() = viewModelScope.launch{
        if (uiState.productFormModel.isValid()) {
            productRepository.insertProduct(uiState.productFormModel.toProduct())
        }
    }
}


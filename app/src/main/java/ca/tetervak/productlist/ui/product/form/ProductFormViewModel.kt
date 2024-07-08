package ca.tetervak.productlist.ui.product.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ca.tetervak.productlist.domain.Condition
import ca.tetervak.productlist.ui.product.model.ProductFormModel
import java.util.Date

abstract class ProductFormViewModel(
): ViewModel() {

    var uiState: ProductFormUiState by mutableStateOf(ProductFormUiState())
        protected set

    fun onNameChange(newName: String){
        val newFormModel = uiState.productFormModel.copy(name = newName)
        uiState = uiState.copy(
            productFormModel = newFormModel,
            isEntryValid = newFormModel.isValid()
        )
    }

    fun onPriceChange(newPrice: String) {
        val newFormModel = uiState.productFormModel.copy(price = newPrice)
        uiState = uiState.copy(
            productFormModel = newFormModel,
            isEntryValid = newFormModel.isValid()
        )
    }

    fun onQuantityChange(newQuantity: String){
        val newFormModel = uiState.productFormModel.copy(quantity = newQuantity)
        uiState = uiState.copy(
            productFormModel = newFormModel,
            isEntryValid = newFormModel.isValid()
        )
    }

    fun onConditionChange(newCondition: Condition){
        val newFormModel = uiState.productFormModel.copy(condition = newCondition)
        uiState = uiState.copy(productFormModel = newFormModel)
    }

    fun onRatingChange(newRating: Float){
        val newFormModel = uiState.productFormModel.copy(rating = newRating)
        uiState = uiState.copy(productFormModel = newFormModel)
    }

    fun onDateChange(newDate: Date){
        val newFormModel = uiState.productFormModel.copy(date = newDate)
        uiState = uiState.copy(productFormModel = newFormModel)
    }

}
package ca.tetervak.productlist.ui.product.entry

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ca.tetervak.productlist.ui.common.ProductListTopAppBar
import ca.tetervak.productlist.ui.product.form.ProductFormBody
import ca.tetervak.productlist.ui.product.form.ProductFormUiState
import ca.tetervak.productlist.ui.product.model.ProductFormModel
import ca.tetervak.productlist.ui.theme.ProductListTheme
import ca.tetervak.productlist.ui.navigation.ProductEntryDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: ProductEntryViewModel
) {
    Scaffold(
        topBar = {
            ProductListTopAppBar(
                title = stringResource(ProductEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        ProductFormBody(
            productFormUiState = viewModel.uiState,
            onNameChange = viewModel::onNameChange,
            onPriceChange = viewModel::onPriceChange,
            onQuantityChange = viewModel::onQuantityChange,
            onConditionChange = viewModel::onConditionChange,
            onRatingChange = viewModel::onRatingChange,
            onDateChange = viewModel::onDateChange,
            onSaveClick = {
                viewModel.saveProduct()
                navigateBack()
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductEntryScreenPreview() {
    ProductListTheme {
        ProductFormBody(
            productFormUiState = ProductFormUiState(
                ProductFormModel(
                    name = "Item name", price = "10.00", quantity = "5"
                )
            ),
            onNameChange = {}, onPriceChange = {}, onQuantityChange = {},
            onConditionChange = {}, onRatingChange = {}, onDateChange = {},
            onSaveClick = {}
        )
    }
}

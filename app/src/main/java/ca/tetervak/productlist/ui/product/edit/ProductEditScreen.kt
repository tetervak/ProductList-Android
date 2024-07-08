package ca.tetervak.productlist.ui.product.edit

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ca.tetervak.productlist.ui.common.ProductListTopAppBar
import ca.tetervak.productlist.ui.product.form.ProductFormBody
import ca.tetervak.productlist.ui.navigation.ProductEditDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: ProductEditViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ProductListTopAppBar(
                title = stringResource(ProductEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
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
                viewModel.updateProduct()
                navigateBack()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

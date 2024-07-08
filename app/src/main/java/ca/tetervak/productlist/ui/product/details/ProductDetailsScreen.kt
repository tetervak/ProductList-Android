package ca.tetervak.productlist.ui.product.details

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ca.tetervak.productlist.R
import ca.tetervak.productlist.domain.Product
import ca.tetervak.productlist.ui.common.ProductListTopAppBar
import ca.tetervak.productlist.ui.common.conditionToString
import ca.tetervak.productlist.ui.common.formatDate
import ca.tetervak.productlist.ui.common.formatTime
import ca.tetervak.productlist.ui.product.model.ProductDetailsModel
import ca.tetervak.productlist.ui.theme.ProductListTheme
import ca.tetervak.productlist.ui.navigation.ProductDetailsDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    navigateToEditProduct: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ProductDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            ProductListTopAppBar(
                title = stringResource(ProductDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditProduct(uiState.value.detailsModel.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_product_title),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        ProductDetailsBody(
            productDetailsUiState = uiState.value,
            onSellProduct = { viewModel.reduceQuantityByOne() },
            onDelete = {
                viewModel.deleteProduct()
                navigateBack()
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun ProductDetailsBody(
    productDetailsUiState: ProductDetailsUiState,
    onSellProduct: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }
        ProductDetails(
            detailsModel = productDetailsUiState.detailsModel, modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSellProduct,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = !productDetailsUiState.outOfStock
        ) {
            Text(stringResource(R.string.sell))
        }
        OutlinedButton(
            onClick = { showConfirmationDialog = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (showConfirmationDialog) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    showConfirmationDialog = false
                    onDelete()
                },
                onDeleteCancel = { showConfirmationDialog = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}


@Composable
fun ProductDetails(
    detailsModel: ProductDetailsModel, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            ProductDetailsRow(
                labelResID = R.string.product,
                productDetail = detailsModel.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ProductDetailsRow(
                labelResID = R.string.quantity_in_stock,
                productDetail = detailsModel.quantity.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ProductDetailsRow(
                labelResID = R.string.price,
                productDetail = detailsModel.price,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )
            ProductDetailsRow(
                labelResID = R.string.condition,
                productDetail = conditionToString(condition = detailsModel.condition),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )
            ProductDetailsRow(
                labelResID = R.string.rating,
                productDetail = stringResource(id = R.string.rating_info, detailsModel.rating),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )
            ProductDetailsRow(
                labelResID = R.string.date,
                productDetail = formatDate(detailsModel.date),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )
            ProductDetailsRow(
                labelResID = R.string.time,
                productDetail = formatTime(detailsModel.date),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )
        }

    }
}

@Composable
private fun ProductDetailsRow(
    @StringRes labelResID: Int, productDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = productDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenPreview() {
    ProductListTheme {
        ProductDetailsBody(
            ProductDetailsUiState(
                Product(
                    1,
                    "Pen",
                    6.25,
                    0
                )
        ), onSellProduct = {}, onDelete = {})
    }
}

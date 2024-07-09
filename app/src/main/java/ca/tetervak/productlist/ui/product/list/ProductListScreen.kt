package ca.tetervak.productlist.ui.product.list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.tetervak.productlist.R
import ca.tetervak.productlist.domain.Product
import ca.tetervak.productlist.ui.common.ProductListTopAppBar
import ca.tetervak.productlist.ui.common.conditionToString
import ca.tetervak.productlist.ui.product.model.ProductListItemModel
import ca.tetervak.productlist.ui.product.model.toProductListItemModel
import ca.tetervak.productlist.ui.navigation.ProductListDestination
import ca.tetervak.productlist.ui.theme.ProductListTheme

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductListScreen(
    navigateToProductEntry: () -> Unit,
    navigateToProductDetails: (Int) -> Unit,
    viewModel: ProductListViewModel,
    modifier: Modifier = Modifier
) {
    val productListUiState: ProductListUiState by viewModel.productListUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProductListTopAppBar(
                title = stringResource(ProductListDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToProductEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.product_entry_title)
                )
            }
        },
    ) { innerPadding ->
        ProductListBody(
            productList = productListUiState.productList,
            onItemClick = navigateToProductDetails,
            onToggleSelect = viewModel::toggleSelect,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun ProductListBody(
    productList: List<ProductListItemModel>,
    onItemClick: (Int) -> Unit,
    onToggleSelect: (ProductListItemModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (productList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_product_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ProductList(
                productList = productList,
                onItemClick = onItemClick,
                onToggleSelect = onToggleSelect,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun ProductList(
    productList: List<ProductListItemModel>,
    onItemClick: (Int) -> Unit,
    onToggleSelect: (ProductListItemModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = productList, key = { it.id }) { item ->
            ProductListItem(
                listItemModel = item,
                onToggleSelect = onToggleSelect,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item.id) })
        }
    }
}

@Composable
private fun ProductListItem(
    listItemModel: ProductListItemModel,
    onToggleSelect: (ProductListItemModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            modifier = modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_small))
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = listItemModel.selected,
                    onCheckedChange = { onToggleSelect(listItemModel)}
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
                ){
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = listItemModel.name,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(
                                R.string.condition_info,
                                conditionToString(condition = listItemModel.condition),
                            ),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = listItemModel.price,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Text(
                        text = stringResource(
                            R.string.stock_info,
                            listItemModel.quantity
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Text(
                text = listItemModel.date,
                fontStyle = FontStyle.Italic,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(start = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    ProductListTheme {
        ProductListBody(listOf(
            Product(1, "Game", 100.0, 20).toProductListItemModel(),
            Product(2, "Pen", 200.0, 30, true).toProductListItemModel(),
            Product(3, "TV", 300.0, 50).toProductListItemModel()
        ), onItemClick = {}, onToggleSelect = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    ProductListTheme {
        ProductListBody(listOf(), onItemClick = {}, onToggleSelect = {})
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    ProductListTheme{
        ProductListItem(
            Product(1, "Game", 100.0, 20).toProductListItemModel(),
            onToggleSelect = {}
        )
    }
}

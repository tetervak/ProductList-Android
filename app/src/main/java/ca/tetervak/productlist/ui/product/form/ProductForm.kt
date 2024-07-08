package ca.tetervak.productlist.ui.product.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ca.tetervak.productlist.R
import ca.tetervak.productlist.domain.Condition
import ca.tetervak.productlist.ui.common.formatDate
import ca.tetervak.productlist.ui.common.formatTime
import ca.tetervak.productlist.ui.product.model.ProductFormModel
import ca.tetervak.productlist.ui.theme.ProductListTheme
import java.util.Currency
import java.util.Date
import java.util.Locale
@Composable
fun ProductForm(
    productFormModel: ProductFormModel,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onConditionChange: (Condition) -> Unit,
    onRatingChange: (Float) -> Unit,
    onDateChange: (Date) -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = productFormModel.name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.product_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = productFormModel.price,
            onValueChange = onPriceChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.product_price_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = productFormModel.quantity,
            onValueChange = onQuantityChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.quantity_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.rating),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.condition),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){
            OutlinedButton(onClick = {  }) {
                Text(text = formatDate(productFormModel.date))
            }
            OutlinedButton(onClick = { }) {
                Text(text = formatTime(productFormModel.date))
            }
        }
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductFormPreview() {
    ProductListTheme {
        ProductForm(
            productFormModel =
                ProductFormModel(
                    name = "Item name", price = "10.00", quantity = "5"
                ),
            onNameChange = {}, onPriceChange = {}, onQuantityChange = {},
            onConditionChange = {}, onRatingChange = {}, onDateChange = {}
        )
    }
}
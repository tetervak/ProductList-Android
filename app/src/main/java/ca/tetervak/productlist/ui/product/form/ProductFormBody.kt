package ca.tetervak.productlist.ui.product.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import ca.tetervak.productlist.R
import ca.tetervak.productlist.domain.Condition
import java.util.Date

@Composable
fun ProductFormBody(
    productFormUiState: ProductFormUiState,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onConditionChange: (Condition) -> Unit,
    onRatingChange: (Float) -> Unit,
    onDateChange: (Date) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        ProductForm(
            productFormModel = productFormUiState.productFormModel,
            onNameChange = onNameChange,
            onPriceChange = onPriceChange,
            onQuantityChange = onQuantityChange,
            onConditionChange = onConditionChange,
            onRatingChange = onRatingChange,
            onDateChange = onDateChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = productFormUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}
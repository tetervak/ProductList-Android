package ca.tetervak.productlist.ui.product.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import java.util.Calendar
import java.util.Currency
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
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
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    // date picker component
    if (showDatePicker) {
        val day: Calendar = Calendar.getInstance().apply {
            timeInMillis = productFormModel.date.time
        }
        val zoneOffset = day.timeZone.getOffset(day.time.time)
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = day.timeInMillis + zoneOffset,
            yearRange = 2024..2027
        )
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selected: Calendar = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis !! - zoneOffset
                        }
                        val calendar: Calendar = Calendar.getInstance().apply {
                            timeInMillis = productFormModel.date.time
                            set(
                                selected.get(Calendar.YEAR),
                                selected.get(Calendar.MONTH),
                                selected.get(Calendar.DAY_OF_MONTH)
                            )
                        }
                        onDateChange(calendar.time)
                        showDatePicker = false
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    // time picker component
   if (showTimePicker) {
       val calendar: Calendar = Calendar.getInstance().apply { time = productFormModel.date }
       val timePickerState = rememberTimePickerState(
           initialHour = calendar.get(Calendar.HOUR_OF_DAY),
           initialMinute = calendar.get(Calendar.MINUTE),
           is24Hour = false
       )
        TimePickerDialog(
            onDismissRequest = {
                showTimePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        with(calendar){
                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            set(Calendar.MINUTE, timePickerState.minute)
                        }
                        onDateChange(calendar.time)
                        showTimePicker = false
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }

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
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.rating),
                style = MaterialTheme.typography.bodyLarge
            )
            RatingInput(
                rating = productFormModel.rating,
                onRatingChange = onRatingChange
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.condition),
                style = MaterialTheme.typography.bodyLarge
            )
            ConditionInput(
                condition = productFormModel.condition,
                onConditionChange = onConditionChange
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){
            OutlinedButton(
                onClick = { showDatePicker = true }
            ) {
                Text(text = formatDate(productFormModel.date))
            }
            OutlinedButton(
                onClick = { showTimePicker = true }
            ) {
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
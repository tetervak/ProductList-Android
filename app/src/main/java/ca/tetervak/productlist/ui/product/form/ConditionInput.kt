package ca.tetervak.productlist.ui.product.form

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.viewinterop.AndroidView
import ca.tetervak.productlist.R
import ca.tetervak.productlist.domain.Condition

@Composable
fun ConditionInput(
    condition: Condition,
    onConditionChange: (Condition)->Unit,
    modifier: Modifier = Modifier
){
    val conditions: Array<String> = stringArrayResource(R.array.product_condition)

    AndroidView(
        //modifier = Modifier.fillMaxWidth(),
        modifier = modifier,
        factory = { context ->
            Spinner(context).apply {
                adapter =
                    ArrayAdapter(
                        context,
                        android.R.layout.simple_spinner_dropdown_item,
                        conditions
                    )
            }
        },
        update = { spinner ->
            spinner.setSelection(condition.ordinal)
            spinner.onItemSelectedListener = ConditionSpinnerAdapter(onConditionChange)
        }
    )
}

class ConditionSpinnerAdapter(
    val onConditionChange: (Condition) -> Unit
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onConditionChange(Condition.entries[position])
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onConditionChange(Condition.NEW)
    }
}
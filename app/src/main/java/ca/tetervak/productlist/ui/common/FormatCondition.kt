package ca.tetervak.productlist.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import ca.tetervak.productlist.R
import ca.tetervak.productlist.domain.Condition

@Composable
fun conditionToString(condition: Condition) =
    stringArrayResource(id = R.array.product_condition)[condition.ordinal]
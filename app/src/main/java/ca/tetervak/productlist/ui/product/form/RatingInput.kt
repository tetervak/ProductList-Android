package ca.tetervak.productlist.ui.product.form

import android.widget.RatingBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun RatingInput(
    rating: Float,
    onRatingChange: (Float) -> Unit,
    modifier: Modifier = Modifier
){
    AndroidView(
        factory = { context ->
            RatingBar(context).apply {
                stepSize = 1.0f
                numStars = 5
            }
        },
        update = { ratingBar ->
            ratingBar.rating = rating
            ratingBar.setOnRatingBarChangeListener { _, _, _ ->
                onRatingChange(ratingBar.rating)
            }
        },
        modifier = modifier
    )
}
package ca.tetervak.productlist.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.tetervak.productlist.domain.Condition
import java.util.Date

@Entity(tableName = "products")
data class LocalProduct(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int,
    val selected: Boolean,
    val condition: Condition,
    val rating: Float,
    @ColumnInfo(name = "time_stamp") val date: Date
)

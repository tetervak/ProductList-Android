package ca.tetervak.productlist.data.local

import androidx.room.TypeConverter
import ca.tetervak.productlist.domain.Condition
import java.util.*

class Converters {

    @TypeConverter
    fun fromLongToDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun fromDateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromConditionToInt(condition: Condition): Int{
        return condition.ordinal
    }

    @TypeConverter
    fun fromIntToCondition(code: Int): Condition {
        return Condition.entries[code]
    }
}
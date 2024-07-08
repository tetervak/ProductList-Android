package ca.tetervak.productlist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalProduct::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
}

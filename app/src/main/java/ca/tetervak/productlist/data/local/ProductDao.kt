package ca.tetervak.productlist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * from products ORDER BY name ASC")
    fun getAllProductsStream(): Flow<List<LocalProduct>>

    @Query("SELECT * from products WHERE id = :id")
    fun getProductByIdStream(id: Int): Flow<LocalProduct?>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: LocalProduct)

    @Update
    suspend fun updateProduct(product: LocalProduct)

    @Query("UPDATE products SET quantity = :quantity WHERE id = :id")
    suspend fun updateProductQuantityById(id: Int, quantity: Int)

    @Query("UPDATE products SET selected = :selected WHERE id = :id")
    suspend fun updateProductSelectedById(id: Int, selected: Boolean)

    @Delete
    suspend fun deleteProduct(item: LocalProduct)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProductById(id: Int)
}

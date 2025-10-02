package ca.tetervak.productlist.data

import android.content.Context
import androidx.room.Room
import ca.tetervak.productlist.data.local.ProductDatabase
import ca.tetervak.productlist.data.local.ProductDao
import ca.tetervak.productlist.data.repository.LocalProductRepository
import ca.tetervak.productlist.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideProductDatabase(
        @ApplicationContext context: Context
    ): ProductDatabase = Room.databaseBuilder(context, ProductDatabase::class.java, "product_database")
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()

    @Singleton
    @Provides
    fun provideProductDao(
        database: ProductDatabase
    ): ProductDao = database.productDao()


    @Singleton
    @Provides
    fun provideProductRepository(
        productDao: ProductDao
    ): ProductRepository = LocalProductRepository(productDao)
}
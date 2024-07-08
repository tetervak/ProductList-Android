/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.tetervak.productlist.data.repository

import ca.tetervak.productlist.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllProductsStream(): Flow<List<Product>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getProductByIdStream(id: Int): Flow<Product?>

    /**
     * Insert item in the data source
     */
    suspend fun insertProduct(product: Product)

    /**
     * Delete item from the data source
     */
    suspend fun deleteProduct(product: Product)

    suspend fun deleteProductById(id: Int)

    /**
     * Update item in the data source
     */
    suspend fun updateProduct(product: Product)

    suspend fun updateProductQuantityById(id: Int, quantity: Int)

    suspend fun updateProductSelectedById(id: Int, selected: Boolean)


}

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

package ca.tetervak.productlist.domain

import java.util.Date

data class Product(
    val id: Int = 0,
    val name: String = "No Name",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val selected: Boolean = false,
    val condition: Condition = Condition.NEW,
    val rating: Float = 0.0F,
    val date: Date = Date()
)

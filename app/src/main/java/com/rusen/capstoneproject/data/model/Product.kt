package com.rusen.capstoneproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String? = null,
    val price: Double? = null,
    @ColumnInfo(name = "sale_price")
    val salePrice: Double? = null,
    val description: String? = null,
    val category: String? = null,
    @ColumnInfo(name = "image_one")
    val imageOne: String? = null,
    @ColumnInfo(name = "image_two")
    val imageTwo: String? = null,
    @ColumnInfo(name = "image_three")
    val imageThree: String? = null,
    val rate: Double? = null,
    val count: Int? = null,
    @ColumnInfo(name = "sale_state")
    val saleState: Boolean? = false
) {
    var favorite: Boolean = false
}
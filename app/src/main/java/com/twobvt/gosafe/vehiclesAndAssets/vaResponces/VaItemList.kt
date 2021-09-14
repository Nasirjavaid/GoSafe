package com.twobvt.gosafe.vehiclesAndAssets.vaResponces




data class VaItemList(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)
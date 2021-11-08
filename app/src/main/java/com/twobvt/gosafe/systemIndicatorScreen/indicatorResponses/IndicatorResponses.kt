package com.twobvt.gosafe.systemIndicatorScreen.indicatorResponses

data class IndicatorResponses(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)
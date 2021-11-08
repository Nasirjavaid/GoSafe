package com.twobvt.gosafe.historyReplay

data class Data(
    val Count: Int,
    val ErrorMessage: Any,
    val History: List<History>,
    val History_Period: String,
    val geofence: List<Any>
)
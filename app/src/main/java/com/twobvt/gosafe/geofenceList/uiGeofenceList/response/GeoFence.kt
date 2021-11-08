package com.twobvt.gosafe.geofenceList.uiGeofenceList.response

data class GeoFence(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)
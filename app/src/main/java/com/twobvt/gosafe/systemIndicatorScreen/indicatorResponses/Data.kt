package com.twobvt.gosafe.systemIndicatorScreen.indicatorResponses

data class Data(

    val AlarmDetail: String,
    val GPS: String,

    val ID: Int,
    val Location: String,
    val Mode: String,

    val Speed: Double,
    val alarm: String,
    val alm_id: Int,
    val device_id: String,
    val dir_angle: Double,
    val gps_datetime: String,
    val lat: Double,
    val lng: Double,
    val loc_status: String,
    val packet_no: String,
    val popup: String,
    val rec_datetime: String,
    val ref_dist: Double,
    val sms: String,
    val veh_id: Int,
    val veh_reg_no: String,
    val veh_status: String
)
package com.twobvt.gosafe.vehiclesAndAssets.vaResponces

data class SubMenu(
    val Status: Any,
    val SubMenu: List<SubItemOfSubMenu>,
    val datatrack: Any,
    val device_id: Any,
    val grp_id: Int,
    val grp_level: Any,
    val grp_name: String,
    val grp_sub_station: Any,
    val grp_trk_id: Any,
    val parent_grp_id: Int,
    val parent_grp_trk_id: Any,
    val pmd: Any,
    val usr_id: Int,
    val vrn: Any
)
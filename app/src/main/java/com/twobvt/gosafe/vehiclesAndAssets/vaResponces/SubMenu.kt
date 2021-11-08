package com.twobvt.gosafe.vehiclesAndAssets.vaResponces

data class SubMenu(
    var Status: Any,
    var SubMenu: List<SubMenu>,
    var datatrack: Any,
    var device_id: Any,
    var grp_id: Int,
    var grp_level: Any,
    var grp_name: String,
    var grp_sub_station: Any,
    var grp_trk_id: Any,
    var parent_grp_id: Int,
    var parent_grp_trk_id: Any,
    var pmd: Any,
    var usr_id: Int,
    var vrn: Any
)


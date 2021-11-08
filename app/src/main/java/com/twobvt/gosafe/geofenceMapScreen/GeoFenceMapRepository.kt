package com.twobvt.gosafe.geofenceMapScreen

import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu

open class GeoFenceMapRepository : BaseRepository()  {


    companion object{

        private lateinit var  eachitemView:Data

        fun getVehicle() : Data {

            //  println("data  in  map repo $subMenuItemGlobal")
            return  eachitemView
        }

    }

    fun sendVehicles(eachitemView:Data){

        Companion.eachitemView = eachitemView
        // println("data received in map repo $subMenuItemGlobal")

    }



}
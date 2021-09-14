package com.twobvt.gosafe.map.mapRepository

import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu

open class MapRepository : BaseRepository()  {


    companion object{

        private lateinit var  subMenuItemGlobal:SubMenu

        fun getVehicle() : SubMenu {

          //  println("data  in  map repo $subMenuItemGlobal")
            return  subMenuItemGlobal
        }

    }

    fun sendVehicles(subMenuItem:SubMenu){

        subMenuItemGlobal = subMenuItem
       // println("data received in map repo $subMenuItemGlobal")

    }



}
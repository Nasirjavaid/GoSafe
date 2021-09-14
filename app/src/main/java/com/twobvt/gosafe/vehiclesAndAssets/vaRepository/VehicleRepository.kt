package com.twobvt.gosafe.vehiclesAndAssets.vaRepository

import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu

open class VehicleRepository() : BaseRepository() {


    companion object{

        private var  subMenuItemListGlobal:List<SubMenu> = ArrayList()

     fun getVehiclesList() : List<SubMenu> {

         //println("data  in  Vehicles repo ${subMenuItemListGlobal.size}")
        return  subMenuItemListGlobal
    }

    }

    fun sendVehiclesList(subMenuItemList:List<SubMenu>){

        subMenuItemListGlobal = subMenuItemList
       // println("data received in repo ${subMenuItemListGlobal.size}")

    }


}


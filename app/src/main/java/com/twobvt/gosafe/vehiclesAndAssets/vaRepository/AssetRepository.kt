package com.twobvt.gosafe.vehiclesAndAssets.vaRepository

import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu

class AssetRepository : BaseRepository() {




    companion object{
        private var  subMenuItemListGlobal:List<SubMenu> = ArrayList()
    fun getAssetList() : List<SubMenu> {

        return  subMenuItemListGlobal
    }}
    fun sendAssetList(subMenuItemList:List<SubMenu>){

        subMenuItemListGlobal = subMenuItemList
        println("data received in repo ${subMenuItemListGlobal.size}")

    }
}
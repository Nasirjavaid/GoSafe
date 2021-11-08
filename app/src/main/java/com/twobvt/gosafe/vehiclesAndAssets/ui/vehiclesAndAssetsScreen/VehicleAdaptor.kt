package com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.twobvt.gosafe.R
import com.twobvt.gosafe.map.mapRepository.MapRepository
import com.twobvt.gosafe.map.mapScreen.MapScreen
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu
import com.twobvt.packetparcer.PacketParser
import kotlinx.android.synthetic.main.grouped_main_ticket.view.*

open class VehicleAdaptor :


    RecyclerView.Adapter<VehicleAdaptor.DataViewHolder>()  {
    private lateinit var dummySubMenu : SubMenu
    lateinit var dummySubMenuList : ArrayList<SubMenu>
    var subMenuList: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = ArrayList()
    var onItemClick: ((List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) -> Unit)? = null

    private  var mapRepository: MapRepository = MapRepository()


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(subMenuList)

                println("Item clicked $adapterPosition")
                print("List size of Sub Menu +++++++  = ${subMenuList[adapterPosition].SubMenu.size}")
                mapRepository.sendVehicles(subMenuList[adapterPosition])
                itemView.context.startActivity(Intent(itemView.context, MapScreen::class.java))

//                val intent = Intent(itemView.context,MapScreen::class.java);
//                intent.putExtra("itemName", userName)
//                itemView.context.startActivity(intent);

            }

            itemView.setOnLongClickListener(OnLongClickListener { v ->
                Toast.makeText(v.context, "Position is $adapterPosition", Toast.LENGTH_SHORT).show()


                false
            })


        }

        fun bind(result: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {

            var packetData = PacketParser.packetParserFun(result.get(adapterPosition).datatrack.toString())
            if(result.get(adapterPosition).grp_name == null)
            {
                itemView.device_name.text = "Group Name 0$adapterPosition"
            }else{
                itemView.device_name.text = result.get(adapterPosition).grp_name

            }
//            itemView.speed.text = packetData.speed
//            itemView.phone_number.text = result.get(adapterPosition).device_id.toString()
            itemView.vehicle_address.text = "Not Available in API ( Not Available )"

//            if(result.get(adapterPosition).device_id.toString()== null)
//            {
//                itemView.device_name.text=="N/A"
//            }else{
//                itemView.device_name.text = result.get(adapterPosition).grp_name
//            }


            if(subMenuList[adapterPosition].SubMenu.isNotEmpty()) {


                val vehicleChildMembersAdapter =
                    VehicleChildMembersAdapter(result[adapterPosition].SubMenu)
                itemView.child_recycler_view.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                itemView.child_recycler_view.adapter = vehicleChildMembersAdapter
            }
            else{
                if(adapterPosition == 2 ||adapterPosition == 5 || adapterPosition == 9 || adapterPosition == 12 ){
                    loaddata(itemView)
                }


            }

        }
    }


    //For dummy Data purpose
     fun loaddata(itemView: View) {
        dummySubMenuList = ArrayList()
        dummySubMenu =  SubMenu("",dummySubMenuList,"","",0,"","","","",0,"","",0,"")


        for (i in 0..1) {

            dummySubMenu.grp_name = "Null     "

            dummySubMenu.device_id = "12-03-2021"
            dummySubMenu.Status = "12:00 pm"
            dummySubMenuList!!.add(dummySubMenu)
        }



        val vehicleChildMembersAdapter = VehicleChildMembersAdapter(dummySubMenuList)
        itemView.child_recycler_view.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL,false)
        itemView.child_recycler_view.adapter = vehicleChildMembersAdapter
//        indicatorAdapter.addData(dummySubMenuList)
        vehicleChildMembersAdapter!!.notifyDataSetChanged()

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.grouped_main_ticket, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(subMenuList)

        holder.itemView.setLongClickable(true);
    }

    override fun getItemCount(): Int = subMenuList.size


    fun addData(list: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {
        subMenuList = list
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
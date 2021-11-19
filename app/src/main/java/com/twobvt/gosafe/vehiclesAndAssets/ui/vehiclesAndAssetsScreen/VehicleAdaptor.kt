package com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.twobvt.gosafe.R
import com.twobvt.gosafe.map.mapRepository.MapRepository
import com.twobvt.gosafe.map.mapScreen.MapScreen
<<<<<<< Updated upstream
=======
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu
import com.twobvt.packetparcer.PacketParser
import kotlinx.android.synthetic.main.asset_main_ticket.view.*
>>>>>>> Stashed changes
import kotlinx.android.synthetic.main.grouped_main_ticket.view.*
import kotlinx.android.synthetic.main.grouped_main_ticket.view.child_recycler_view
import kotlinx.android.synthetic.main.grouped_main_ticket.view.device_name
import kotlinx.android.synthetic.main.grouped_main_ticket.view.vehicle_address

open class VehicleAdaptor :
    RecyclerView.Adapter<VehicleAdaptor.DataViewHolder>()  {

    var subMenuList: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = ArrayList()
    var onItemClick: ((List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) -> Unit)? = null

    private  var mapRepository: MapRepository = MapRepository()


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { v ->
//                val imm = v.getContext()
//                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                itemView.requestFocus()

                onItemClick?.invoke(subMenuList)

                println("Item clicked $adapterPosition")
<<<<<<< Updated upstream
=======
                print("List size of Sub Menu +++++++  = ${subMenuList[adapterPosition].SubMenu.size}")

>>>>>>> Stashed changes
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

<<<<<<< Updated upstream

            if(result.get(adapterPosition).grp_name== null)
            {
                itemView.device_name.text=="N/A"
            }else{
                itemView.device_name.text = result.get(adapterPosition).grp_name
            }
=======
            var packetData = PacketParser.packetParserFun(result.get(adapterPosition).datatrack.toString())
            if(result.get(adapterPosition).device_id == null)
            {
                itemView.device_name.text = "ID Not Available"
            }else{
                itemView.device_name.text = "${result.get(adapterPosition).device_id.toString()} Group-$adapterPosition"

            }
            if(adapterPosition == 0)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_zero)
            if(adapterPosition == 1)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_one)
            if(adapterPosition == 2)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_two)
            if(adapterPosition == 3)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_three)
            if(adapterPosition == 4)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_four)
            if(adapterPosition == 5)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_five)
            if(adapterPosition == 6)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_one)
            if(adapterPosition == 7)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_zero)
            if(adapterPosition == 8)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_one)
            if(adapterPosition == 9)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_two)
            if(adapterPosition == 10)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_three)
            if(adapterPosition == 11)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_four)
            if(adapterPosition == 12)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_five)
            if(adapterPosition == 13)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_one)
            if(adapterPosition == 14)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_zero)
            if(adapterPosition == 15)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_one)
            if(adapterPosition == 16)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_two)
            if(adapterPosition == 17)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_three)
            if(adapterPosition == 18)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_four)
            if(adapterPosition == 19)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_five)
            if(adapterPosition == 20)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_one)
            if(adapterPosition == 21)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_zero)
            if(adapterPosition == 22)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_one)
            if(adapterPosition == 23)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_two)
            if(adapterPosition == 24)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_three)
            if(adapterPosition == 25)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_four)
            if(adapterPosition == 26)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_five)
            if(adapterPosition == 27)  itemView.image_view_grouped.setImageResource(R.drawable.vehicle_list_one)
//            itemView.speed.text = packetData.speed
//            itemView.phone_number.text = result.get(adapterPosition).device_id.toString()
            itemView.vehicle_address.text = "Not Available in API ( Not Available )"
>>>>>>> Stashed changes

//            if(result.get(adapterPosition).device_id.toString()== null)
//            {
//                itemView.device_name.text=="N/A"
//            }else{
//                itemView.device_name.text = result.get(adapterPosition).grp_name
//            }

            val vehicleChildMembersAdapter = VehicleChildMembersAdapter(result.get(adapterPosition).SubMenu)
            itemView.child_recycler_view.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            itemView.child_recycler_view.adapter = vehicleChildMembersAdapter
        }
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
//    fun dismissKeyboard(activity: Activity) {
//        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
//            activity.currentFocus!!.applicationWindowToken, 0
//        )
//    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
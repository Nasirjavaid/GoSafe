package com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.twobvt.gosafe.R
import com.twobvt.gosafe.config.visible
import com.twobvt.gosafe.vehiclesAndAssets.vaViewModel.VehicleSelectionViewModel
import kotlinx.android.synthetic.main.grouped_main_ticket.view.*





open class VehicleAdaptor :
    RecyclerView.Adapter<VehicleAdaptor.DataViewHolder>()  {

    var subMenuList: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = ArrayList()
    var onItemClick: ((List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) -> Unit)? = null

   var viewModel: VehicleSelectionViewModel =VehicleSelectionViewModel()


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(subMenuList)

                println("Item clicked $adapterPosition")

            }

            itemView.setOnLongClickListener(OnLongClickListener { v ->
                Toast.makeText(v.context, "Position is $adapterPosition", Toast.LENGTH_SHORT).show()


                itemView.checkbox_main_grouped_ticket.visible(true)
                itemView.checkbox_main_grouped_ticket.isChecked=true

                viewModel.addItemToList(subMenuList[adapterPosition])

                false
            })

            itemView.checkbox_main_grouped_ticket.setOnClickListener{
                itemView.checkbox_main_grouped_ticket.isChecked=false
                itemView.checkbox_main_grouped_ticket.visible(false)

            }

        }

        fun bind(result: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {


            if(result.get(adapterPosition).grp_name== null)
            {
                itemView.device_name.text=="N/A"
            }else{
                itemView.device_name.text = result.get(adapterPosition).grp_name
            }

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


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
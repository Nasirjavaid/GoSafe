package com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twobvt.gosafe.R
import kotlinx.android.synthetic.main.grouped_main_ticket.view.*


open class AssetAdapter :
    RecyclerView.Adapter<AssetAdapter.DataViewHolder>() {

    var subMenuList: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = ArrayList()


    var onItemClick: ((List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(subMenuList)

                println("Item clicked $adapterPosition")

            }
        }




        fun bind(result: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {

            if(result.get(adapterPosition).grp_name== null)
            {
                itemView.device_name.text=="N/A"
            }else{
                itemView.device_name.text = result.get(adapterPosition).grp_name
            }
//            val childMembersAdapter = VehicleChildMembersAdapter(result.get(adapterPosition).SubMenu)
//            itemView.child_recycler_view.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
//            itemView.child_recycler_view.adapter = childMembersAdapter

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.asset_main_ticket, parent,
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


}
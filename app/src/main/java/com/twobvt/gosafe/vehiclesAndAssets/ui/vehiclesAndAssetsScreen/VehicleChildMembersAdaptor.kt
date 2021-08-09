package com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twobvt.gosafe.R
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubItemOfSubMenu
import kotlinx.android.synthetic.main.child_item_grouped_main_ticket.view.*

open class VehicleChildMembersAdapter(subItemOfSubMenu: List<SubItemOfSubMenu>) :
    RecyclerView.Adapter<VehicleChildMembersAdapter.DataViewHolder>() {

    private var subMenuItemList: List<SubItemOfSubMenu> = ArrayList()

    init {
        this.subMenuItemList = subItemOfSubMenu
    }

    var onItemClick: ((String) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(subMenuItemList[adapterPosition].parent_grp_id.toString())
            }
        }

        fun bind(result: SubItemOfSubMenu) {
            itemView.name.text = result.parent_grp_id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.child_item_grouped_main_ticket, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(subMenuItemList[position])
    }

    override fun getItemCount(): Int = subMenuItemList.size


}
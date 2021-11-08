package com.twobvt.gosafe.systemIndicatorScreen.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.twobvt.gosafe.R
import com.twobvt.gosafe.map.mapScreen.MapScreen
import com.twobvt.gosafe.systemIndicatorScreen.indicatorResponses.DummyModel
import kotlinx.android.synthetic.main.grouped_main_ticket.view.*




open class IndicatorAdapter :
    RecyclerView.Adapter<IndicatorAdapter.DataViewHolder>()  {

    var indicatorList: List<DummyModel> = ArrayList()
    var onItemClick: ((List<DummyModel>) -> Unit)? = null


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(indicatorList)

                println("Item clicked $adapterPosition")
               // indicatorRepository.getSystemIndicator(indicatorList[adapterPosition])
                itemView.context.startActivity(Intent(itemView.context, MapScreen::class.java))

//                val intent = Intent(itemView.context,MapScreen::class.java);
//                intent.putExtra("itemName", userName)
//                itemView.context.startActivity(intent);

            }

            itemView.setOnLongClickListener(View.OnLongClickListener { v ->
                Toast.makeText(v.context, "Position is $adapterPosition", Toast.LENGTH_SHORT).show()


                false
            })


        }

        fun bind(result: List<DummyModel>) {


            if(result.get(adapterPosition).alertName== null)
            {
                itemView.device_name.text=="N/A"
            }else{
                itemView.device_name.text = result.get(adapterPosition).alertName
            }

//            if(result.get(adapterPosition).device_id.toString()== null)
//            {
//                itemView.device_name.text=="N/A"
//            }else{
//                itemView.device_name.text = result.get(adapterPosition).grp_name
//            }

//            val vehicleChildMembersAdapter = VehicleChildMembersAdapter(result.get(adapterPosition).)
//            itemView.child_recycler_view.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
//            itemView.child_recycler_view.adapter = vehicleChildMembersAdapter
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.sysytem_indicator_ticket, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(indicatorList)

        holder.itemView.setLongClickable(true);
    }

    override fun getItemCount(): Int = indicatorList.size


    fun addData(list: List<DummyModel>) {
        indicatorList = list
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
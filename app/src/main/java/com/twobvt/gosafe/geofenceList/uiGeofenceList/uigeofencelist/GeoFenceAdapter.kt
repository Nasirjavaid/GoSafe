package com.twobvt.gosafe.geofenceList.uiGeofenceList.uigeofencelist


import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twobvt.gosafe.R
import com.twobvt.gosafe.geofenceMapScreen.GeoFenceMapActivity
import com.twobvt.gosafe.geofenceMapScreen.GeoFenceMapRepository
import com.twobvt.gosafe.map.mapRepository.MapRepository
import com.twobvt.gosafe.map.mapScreen.MapScreen
import kotlinx.android.synthetic.main.geofence_card_layout.view.*
import kotlinx.android.synthetic.main.grouped_main_ticket.view.*
import kotlinx.android.synthetic.main.grouped_main_ticket.view.device_name


open class GeoFenceAdapter :
    RecyclerView.Adapter<GeoFenceAdapter.DataViewHolder>() {

    var geofencesList: List<com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data> = ArrayList()


    var onItemClick: ((List<com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data>) -> Unit)? = null
    private  var geoFenceMapRepository: GeoFenceMapRepository = GeoFenceMapRepository()
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(geofencesList)

                println("Item clicked $adapterPosition")
                geoFenceMapRepository.sendVehicles(geofencesList[adapterPosition])
                var intent = Intent(itemView.context, GeoFenceMapActivity::class.java)
//                intent.putExtra("geofenceString",geofencesList[adapterPosition].FenceParam)
               // intent.putExtra("geofenceString","32.553903,71.563911|32.568082,71.591892|32.579799,71.600475|32.597155,71.603737|32.600192,71.593952|32.598746,71.576443|32.601639,71.568546|32.604097,71.560135|32.601928,71.556015|32.611327,71.546402|32.619714,71.534557|32.619714,71.518764|32.601494,71.521168|32.569818,71.504002|32.557086,71.507263|32.559835,71.516533|32.557955,71.520138|32.551444,71.523743|32.550141,71.534557|32.551588,71.54108|32.544932,71.550865|32.544932,71.559105")
  //              intent.putExtra("geofenceString","31.532513@74.352322")
                //For Rectangle
                if(geofencesList[adapterPosition].gf_type == "Circle"){
                    intent.putExtra("geofenceType","Circle")
                    intent.putExtra("geofenceString",geofencesList[adapterPosition].FenceParam)

                }else if(geofencesList[adapterPosition].gf_type == "Rectangle"){
                    intent.putExtra("geofenceString","31.495910256879288, 74.36657125341361|31.496056628548395, 74.35940439139402|31.490713914196345, 74.35910398400397|31.49085715465043, 74.36662000691386")
                    intent.putExtra("geofenceType","Rectangle")

                }else if(geofencesList[adapterPosition].gf_type == "Polygon"){
                    intent.putExtra("geofenceType","Polygon")
                    intent.putExtra("geofenceString",geofencesList[adapterPosition].FenceParam)
                }

                itemView.context.startActivity(intent)


            }
        }




        fun bind(result: List<com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data>) {
            itemView.device_name.text = result[adapterPosition].gf_name
            if(result[adapterPosition].gf_type == "Rectangle"){
                itemView.geofence_shape.setImageResource(R.drawable.square_geofence)
            }else if(result[adapterPosition].gf_type == "Polygon"){
                itemView.geofence_shape.setImageResource(R.drawable.polygone_geofence_shape)
            }else{
                itemView.geofence_shape.setImageResource(R.drawable.circle_geofence)
            }



//            if(result.get(adapterPosition).grp_name== null)
//            {
//                itemView.device_name.text=="N/A"
//            }else{
//                itemView.device_name.text = result.get(adapterPosition).grp_name
//            }
//            val childMembersAdapter = VehicleChildMembersAdapter(result.get(adapterPosition).SubMenu)
//            itemView.child_recycler_view.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
//            itemView.child_recycler_view.adapter = childMembersAdapter

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.geofence_card_layout, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(geofencesList)
        holder.itemView.isLongClickable = true;
    }

    override fun getItemCount(): Int = geofencesList.size


    fun addData(list: List<com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data>) {
        geofencesList = list
        notifyDataSetChanged()
    }


}
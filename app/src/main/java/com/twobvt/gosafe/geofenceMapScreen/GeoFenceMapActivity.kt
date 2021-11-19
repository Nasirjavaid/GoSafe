package com.twobvt.gosafe.geofenceMapScreen

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Build.ID
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.twobvt.gosafe.R
import com.twobvt.gosafe.base.BaseActivity
import com.twobvt.gosafe.base.BaseFragmentActivity
import com.twobvt.gosafe.databinding.ActivityGeoFenceMapBinding


class GeoFenceMapActivity : BaseActivity<GeofenceMapViewModel, ActivityGeoFenceMapBinding, GeoFenceMapRepository>() , OnMapReadyCallback,GoogleMap.OnMapLongClickListener{


    private lateinit var  googleMap :GoogleMap
    private lateinit var  geofencingClient :GeofencingClient
    private lateinit var  markingOptions :MarkerOptions
    private lateinit var  locationManager: LocationManager
    private lateinit var  geofenceHelper: GeoFenceHelper
    private val locationCode = 2000
    private val locationCode1 = 2001


    private var lat :Double = 0.0
    private val latlngString :String = "32.553903,71.563911|32.568082,71.591892|32.579799,71.600475|32.597155,71.603737|32.600192,71.593952|32.598746,71.576443|32.601639,71.568546|32.604097,71.560135|32.601928,71.556015|32.611327,71.546402|32.619714,71.534557|32.619714,71.518764|32.601494,71.521168|32.569818,71.504002|32.557086,71.507263|32.559835,71.516533|32.557955,71.520138|32.551444,71.523743|32.550141,71.534557|32.551588,71.54108|32.544932,71.550865|32.544932,71.559105"
//    private var  :String = ""
    var latlngStringDynamic: String =   ""
    var geofenceType: String =   ""
    private var lng :Double = 0.0
    private var myMarker: Marker? = null
    private lateinit var subBottomSheetDialogForMapSettingsResetOdometerOne : BottomSheetDialog
    private lateinit var subBottomSheetDialogForMapSettingsResetOdometerTwo : BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latlngStringDynamic = getIntent().getStringExtra("geofenceString").toString()
        geofenceType = getIntent().getStringExtra("geofenceType").toString()
        print("intent strig here")
        print(latlngStringDynamic)



//        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted)
//        {
//            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
//            startActivity(intent)
//        }


        //Toolbar setup
        val toolbar: Toolbar = binding.newToolbar
        toolbar.setTitle("")
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel.getVehicle()
        viewModel.singleItem.observe(this){
            binding.toolbarText.setText(it.gf_name)

        }
//        setActionBar(toolbar)






        loadMapFragment()
//        fabInfoButton()



    }




    private fun loadMapFragment()
    {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.geofence_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        geofencingClient = LocationServices.getGeofencingClient(this)
        geofenceHelper = GeoFenceHelper(this)


// getting data after the ui WORK done
//        getVehicle()

    }

    override fun onMapReady(p0: GoogleMap?) {

        if (p0 != null) {
            googleMap = p0
        }
        var  markerLocation : LatLng
        if(geofenceType == "Circle")
        {
            val list: List<String> = listOf(*latlngStringDynamic!!.split("@").toTypedArray())
             markerLocation =LatLng(list[0].toDouble(), list[1].toDouble())
        }else if(geofenceType == "Polygon"){
            val list: List<String> = listOf(*latlngStringDynamic!!.split("|").toTypedArray())
            val getFirstItem: List<String> = listOf(*list[0]!!.split(",").toTypedArray())
            markerLocation =LatLng(getFirstItem[0].toDouble(), getFirstItem[1].toDouble())

        }else if(geofenceType == "Rectangle"){
            markerLocation =LatLng(31.493861029421378,74.3631809419561)
        }
        else{
             markerLocation =LatLng(51.476706, 0.0)
        }
//

//        getCenterPoint(latlngString)

        markingOptions = MarkerOptions().position(markerLocation).title("Marker in Sydney").snippet("My Current City")
        googleMap.addMarker(markingOptions)

        if(geofenceType == "Rectangle")
        {

            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(markerLocation,15F)
            googleMap.animateCamera(cameraUpdate)
        }else{
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(markerLocation,12F)
            googleMap.animateCamera(cameraUpdate)
        }


        handleMapLongClick(markerLocation)
//        getMyLocation()
//
//        if(Build.VERSION.SDK_INT >= 29)
//        {
//            if(ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//            {
//                handleMapLongClick(markerLocation)
//            }
//            else
//            {
//                if(ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION))
//                {
//                    ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),locationCode1)
//                    Toast.makeText(this,"For Triguring Geofence We Need Background Location Permission",Toast.LENGTH_LONG).show()
//                }
//                else
//                {
//                    ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),locationCode1)
//                }
//            }
//
//        }
//        else{
//            handleMapLongClick(markerLocation)
//        }






    }
//    private fun getCenterPoint(latlngString:String) {
//        val list: List<String> = listOf(*latlngString.split("|").toTypedArray())
//        var latitude = 0.0
//        var longitude = 0.0
//        val n: Int = list.size
//
//        for (point in list) {
//            val item: List<String> = listOf(*point.split(",").toTypedArray())
//            latitude += item[0].toDouble()
//            longitude += item[1].toDouble()
//        }
//
//        return LatLng(latitude / n, longitude / n)
//
//
//
//
//
//
//    }

    private fun getMyLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            googleMap.isMyLocationEnabled =  true

        }
        else
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),locationCode)
            }
            else
            {
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),locationCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == locationCode )
        {
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED )
                {
                    return
                }
                googleMap.isMyLocationEnabled = true
            }

        }
        if(requestCode == locationCode1 )
        {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if(ActivityCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
                {
                    return
                }
             Toast.makeText(this,"Can't show geofence",Toast.LENGTH_LONG).show()

            }
        }
    }
    override fun onMapLongClick(p0: LatLng) {
       if(Build.VERSION.SDK_INT >= 29)
       {
           if(ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
           {
               handleMapLongClick(p0)
           }
           else
           {
               if(ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION))
               {
                   ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),locationCode1)
                   Toast.makeText(this,"For Triguring Geofence We Need Background Location Permission",Toast.LENGTH_LONG).show()
               }
               else
               {
                   ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),locationCode1)
               }
           }

       }
        else{
            handleMapLongClick(p0)
       }
    }
    private fun handleMapLongClick(p0: LatLng) {
        googleMap.clear()
        addMarker(p0)
        if(geofenceType == "Circle")
        {
            addCircle()
        }else if(geofenceType == "Polygon"){
            addPolygon()
        }else if(geofenceType == "Rectangle"){
            addRectangle()
        }
//        addGeofence(p0)
    }
    private fun addMarker(latlng: LatLng)
    {
        markingOptions = MarkerOptions().position(latlng)
        googleMap.addMarker(markingOptions)
    }
    private fun addCircle()
    {
        val circleOptions = CircleOptions()
        val list: List<String> = listOf(*latlngStringDynamic!!.split("@").toTypedArray())
        circleOptions.center(LatLng(list[0].toDouble(),list[1].toDouble()))
        circleOptions.radius(3000.00)
        circleOptions.strokeColor(Color.parseColor("#274778"))
        circleOptions.fillColor(Color.parseColor("#c6dbfc"))
        circleOptions.strokeWidth(4F)




        googleMap.addCircle(circleOptions)


    }
    private fun addPolygon()

    {
        val list: List<String> = listOf(*latlngStringDynamic!!.split("|").toTypedArray())
        val points = ArrayList<LatLng>()
        list.forEachIndexed{index, item ->
//            println("index = $index, item = $item ")
            val item: List<String> = listOf(*list[index].split(",").toTypedArray())
            points.add(LatLng(item[0].toDouble(),item[1].toDouble()))
        }
        print("points are here")
        print(points)
        val opts = PolygonOptions()

        for (point in points) {
            opts.add(point)
        }


//        val polygonOptions = PolygonOptions()
//            .add(
////                points
////                LatLng(51.474821, -0.001935),
////                LatLng(51.474647, 0.003966),
////                LatLng(51.477708, 0.004073),
////                LatLng(51.479753, 0.000468),
////                LatLng(51.477654, -0.002192),
//
//                LatLng(32.553903, 71.563911),
//                LatLng(32.568082, 71.591892),
//                LatLng(32.579799, 71.600475),
//                LatLng(32.597155, 71.603737),
//                LatLng(32.600192, 71.593952),
//                LatLng(32.598746, 71.576443),
//
//                LatLng(32.601639, 71.568546),
//                LatLng(32.604097, 71.560135),
//                LatLng(32.601928, 71.556015),
//                LatLng(32.611327, 71.546402),
//
//                LatLng(32.619714, 71.534557),
//                LatLng(32.619714, 71.518764),
//                LatLng(32.601494, 71.521168),
//                LatLng(32.569818, 71.504002),
//                LatLng(32.557086, 71.507263),
//                LatLng(32.559835, 71.516533),
//
//                LatLng(32.557955, 71.520138),
//                LatLng(32.551444, 71.523743),
//                LatLng(32.550141, 71.534557),
//                LatLng(32.551588, 71.54108),
//                LatLng(32.544932, 71.550865),
//                LatLng(32.544932, 71.559105),
//
//
//            ).strokeColor(Color.parseColor("#274778"))
//            .fillColor(Color.parseColor("#c6dbfc"))
//            .strokeWidth(4F)

        opts.strokeColor(Color.parseColor("#274778"))
            .fillColor(Color.parseColor("#c6dbfc"))
            .strokeWidth(4F)


        googleMap.addPolygon(opts)

    }
    private fun PolygonPoints(){

    }
    private fun addRectangle(){
        val list: List<String> = listOf(*latlngStringDynamic!!.split("|").toTypedArray())
        val points = ArrayList<LatLng>()
        list.forEachIndexed{index, item ->
//            println("index = $index, item = $item ")
            val item: List<String> = listOf(*list[index].split(",").toTypedArray())
            points.add(LatLng(item[0].toDouble(),item[1].toDouble()))
        }
        print("points are here")
        print(points)
        val opts = PolygonOptions()

        for (point in points) {
            opts.add(point)
        }
        opts.strokeColor(Color.parseColor("#274778"))
            .fillColor(Color.parseColor("#c6dbfc"))
            .strokeWidth(4F)

//        val rectOptions = PolygonOptions()
//            .add(
//                LatLng(51.474821, -0.001935),
//                LatLng(51.474647, 0.003966),
//                LatLng(51.477708, 0.004073),
//                LatLng(51.477654, -0.002192),
//
//            ).strokeColor(Color.parseColor("#274778"))
//            .fillColor(Color.parseColor("#c6dbfc"))
//            .strokeWidth(4F)
        googleMap.addPolygon(opts)

    }

    private fun addGeofence(latlng: LatLng)
    {
        val geofence = geofenceHelper.getGeofence(ID,latlng,100.0, Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT)
        val geofenceRequest = geofence.let { geofenceHelper.getGeofencingRequest(it) }
        val pendingIntent = geofenceHelper.pendingIntent
        if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            return
        }
        geofencingClient.addGeofences(geofenceRequest!!,pendingIntent).run {
            addOnSuccessListener {
                Log.d("Success","Geofence Triggered")
            }
            addOnFailureListener {
                Log.d("Failure","Geofence Not Added")
            }
        }
    }


    private fun loadVehicleLocationOnMap()
    {

        val latLng = LatLng(lat, lng)
        myMarker =   googleMap?.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Vehicle Location"),
        )
        // adding tag to marker
        myMarker?.tag  = 2

        val location = CameraUpdateFactory.newLatLngZoom(
            latLng, 15f)
        googleMap?.animateCamera(location)
    }

    private fun getVehicle(){

        //calling vehicle data
        viewModel.getVehicle()
        //calling packet parser
        viewModel.getPacketParser()


        viewModel.singleItem.observe(this){
            //binding.toolbarText.setText(it.grp_name)

        }

        viewModel.packetParser.observe(this){

//            lat= it.lat.toDouble()
//            lng=it.lng.toDouble()
//            binding.toolbarText.setText(it.sim_no)

        }

    }


    override fun getViewModel() = GeofenceMapViewModel::class.java

    override fun getViewBinding()=  ActivityGeoFenceMapBinding.inflate(layoutInflater)

    override fun getRepository()= GeoFenceMapRepository()

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_controls -> {

//                showBottomSheetDialogForMapControls()

            }
            R.id.action_settings -> {

//                showBottomSheetDialogForMapSettings()
            }
            R.id.action_history -> {

//                showBottomSheetDialogForMapHistory()
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.map_screen_menu, menu)
//
//        // return true so that the menu pop up is opened
//        return true
//    }




}






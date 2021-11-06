package com.twobvt.gosafe.historyReplay

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler

import android.os.SystemClock
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.twobvt.gosafe.R
import com.twobvt.gosafe.base.BaseActivity
import com.twobvt.gosafe.config.TinyDB
import com.twobvt.gosafe.config.getAccessToken
import com.twobvt.gosafe.config.handleApiErrors
import com.twobvt.gosafe.databinding.ActivityHistoryReplayBinding
import com.twobvt.gosafe.network.Resource



class HistoryReplayActivity : BaseActivity<HistoryReplayViewModel, ActivityHistoryReplayBinding,HistoryReplayRepository>(),
    OnMapReadyCallback {
    private lateinit var tinyDB: TinyDB
    val latLngPts = ArrayList<LatLng>()




    private lateinit var  googleMap :GoogleMap
    private lateinit var  markingOptions : MarkerOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_history_replay)
        val toolbar: Toolbar = binding.newToolbar
        toolbar.setTitle("")
        setSupportActionBar(toolbar)
        viewModel.historyReplayList()
        //calling packet parser




        viewModel.historyReplayResponse.observe(this){
            when (it) {
                is Resource.Loading -> {
                    println("Loading.................................")
                }
                is Resource.Success -> {
//                    print("latitude and longitude ")
                    print(it.value.data.History[0].Latitude)
                    print(it.value.data.History[0].Longitude)
//                    for (item in it.value.data.History) {
//                        locations[]
//                        // body of loop
//                    }
                    it.value.data.History.forEachIndexed { index, element ->


                        latLngPts.add(LatLng(element.Latitude.toDouble(),element.Longitude.toDouble()))
                    }
                    print(latLngPts)


                }
                is Resource.Failure -> {
                    handleApiErrors(it)
                }

            }


//            toolbar.setTitle(it.)
//            print(it.data.)
            //binding.toolbarText.setText(it.grp_name)
//            print()

        }

//        viewModel.packetParser.observe(this){
//
//            lat= it.lat.toDouble()
//            lng=it.lng.toDouble()
//            binding.toolbarText.setText(it.sim_no)
//
//        }
        loadMapFragment()

//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        viewModel.historyReplayList ()
//        viewModel.singleItem.observe(this){
//            binding.toolbarText.setText(it.gf_name)
//
//        }
    }
    fun setAnimation(myMap: GoogleMap, directionPoint: List<LatLng?>, bitmap: Bitmap?) {
        val marker = myMap.addMarker(MarkerOptions()
            .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            .position(directionPoint[0])
            .flat(true))
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(directionPoint[0], 10f))
        animateMarker(myMap, marker, directionPoint, false)
    }


    private fun animateMarker(
        myMap: GoogleMap, marker: Marker, directionPoint: List<LatLng?>,
        hideMarker: Boolean,
    ) {
        print("inside animate marker")
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val proj = myMap.projection
        val duration: Long = 30000
        val interpolator: Interpolator = LinearInterpolator()
        handler.post(object : Runnable {

            var i = 0
            override fun run() {
                print("inside animate marker1")
                val elapsed = SystemClock.uptimeMillis() - start
                val t = interpolator.getInterpolation(elapsed.toFloat()
                        / duration)
                if (i < directionPoint.size) marker.position = directionPoint[i]
                print("inside animate marker2")
                i++
                if (t < 1.0) {
                    print("inside animate marker3")
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                } else {
                    marker.isVisible = !hideMarker
                }
            }
        })
    }

    private fun loadMapFragment()
    {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)


//        geofencingClient = LocationServices.getGeofencingClient(this)
//        geofenceHelper = GeoFenceHelper(this)


// getting data after the ui WORK done
//        getVehicle()


    }
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        var lat: Double = 31.493861029421378
        var lng: Double = 74.3631809419561

        var  markerLocation : LatLng = LatLng(31.493861029421378,74.3631809419561)
        markingOptions = MarkerOptions().position(markerLocation).title("Marker in Sydney").snippet("My Current City")
        googleMap.addMarker(markingOptions)
//        val location = CameraUpdateFactory.newLatLngZoom(
//            markerLocation, 15f)
//        googleMap?.animateCamera(location)


//


        var  myList: MutableList<LatLng> =mutableListOf()
        myList.add(LatLng(lat+1.1, lng+1.4))
        myList.add(LatLng(lat+0.99, lng+1.8))
        myList.add(LatLng(lat+0.55, lng+1.2))
        myList.add(LatLng(lat+1.5, lng+0.88))
        myList.add(LatLng(lat+1.23, lng+1.45))
        myList.add(LatLng(lat+1.99, lng+1.90))

        myList.add(LatLng(lat, lng))
        animateMarker(googleMap,googleMap.addMarker(markingOptions),myList,false)
       //Already present solution
//        val options = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)
//        myList.forEachIndexed { index, element ->
//            val point: LatLng = element
//            print("now point is $point")
//            options.add(point)
//            print("now options are $options")
//            googleMap!!.addPolyline(options)
//        }
        //End Already present solution

        //Solution One
//        val options = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)
//        for (z in 0 until myList.size) {
//            val point: LatLng = myList.get(z)
//            options.add(point)
//        }
//        googleMap!!.addPolyline(options)
        //Solution One Ended


//            Timer().schedule(timerTask {
//                print("helo00000000000")
//                print(index)
//            }, 2000)

            // do something after 1 second




//            Handler().postDelayed({
//
//            }, 10000)



//        }






//        for (z in 0 until myList.size) {
//            val point: LatLng = myList.get(z)
//            options.add(point)
//            Handler().postDelayed({
//                googleMap!!.addPolyline(options)
//            }, 10000)
//
//
//
//        }
//        googleMap!!.addPolyline(options)
    }



    override fun getViewModel() = HistoryReplayViewModel::class.java

    override fun getViewBinding() = ActivityHistoryReplayBinding.inflate(layoutInflater)

    override fun getRepository(): HistoryReplayRepository {
        lateinit var api: HistoryReplayApi

        try {
            // tiny DB -> shared preferences
            tinyDB = TinyDB(applicationContext)

            var authToken: String = getAccessToken(applicationContext)

            api = remoteDataSource.buildApi(HistoryReplayApi::class.java, authToken)

            return HistoryReplayRepository(api)
        } catch (e: NullPointerException) {
            println("Following exception occurred $e")
        } finally {

            return HistoryReplayRepository(api)
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}
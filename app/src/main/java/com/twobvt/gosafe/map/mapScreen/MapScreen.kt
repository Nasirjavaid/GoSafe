package com.twobvt.gosafe.map.mapScreen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.twobvt.gosafe.R
import com.twobvt.gosafe.base.BaseActivity
import com.twobvt.gosafe.databinding.ActivityMapScreenBinding
import com.twobvt.gosafe.historyReplay.HistoryReplayActivity
import com.twobvt.gosafe.map.mapRepository.MapRepository
import com.twobvt.gosafe.map.mapViewModel.MapViewModel
import kotlinx.android.synthetic.main.activity_map_screen.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*



class MapScreen : BaseActivity<MapViewModel,ActivityMapScreenBinding,MapRepository>() , OnMapReadyCallback{


    private  var  googleMap :GoogleMap? = null
    private lateinit var  markingOptions :MarkerOptions
    private var lat :Double = 0.0
    private var lng :Double = 0.0
    private var myMarker: Marker? = null
    private lateinit var subBottomSheetDialogForMapSettingsResetOdometerOne : BottomSheetDialog
    private lateinit var subBottomSheetDialogForMapSettingsResetOdometerTwo : BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Toolbar setup
        val toolbar: Toolbar = binding.newToolbar
//        toolbar.setTitle("")
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)





        loadMapFragment()
        fabInfoButton()



    }


    private fun fabInfoButton(){

        binding.fabInfo.setOnClickListener{
            showBottomSheetDialogForVehicleInfo()
        }


    }

    private fun loadMapFragment()
    {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

// getting data after the ui WORK done
        getVehicle()

    }

    override fun onMapReady(p0: GoogleMap?) {

        googleMap = p0

        loadVehicleLocationOnMap()

//        var line: Polyline = googleMap!!.addPolyline(PolylineOptions()
//            .add(LatLng(51.5, -0.1), LatLng(31.5204, 74.3587))
//            .width(5f)
//            .color(Color.RED))



        //TODO: DUMMY LINES

        val options = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)


        var  myList: MutableList<LatLng> =mutableListOf()
            myList.add(LatLng(lat+1.1, lng+1.4))
            myList.add(LatLng(lat+0.99, lng+1.8))
            myList.add(LatLng(lat+0.55, lng+1.2))
            myList.add(LatLng(lat+1.5, lng+0.88))
            myList.add(LatLng(lat+1.23, lng+1.45))
            myList.add(LatLng(lat+1.99, lng+1.90))

            myList.add(LatLng(lat, lng))




        for (z in 0 until myList.size) {
            val point: LatLng = myList.get(z)
            options.add(point)
        }
       googleMap!!.addPolyline(options)



//        googleMap!!.setOnMarkerClickListener { marker ->
//           if (marker.tag==2)
//           {
//              showBottomSheetDialog()
//
//           }
//            true
//        }
    }

//    override fun onMarkerClick(p0: Marker): Boolean {
//        val name: String = myMarker?.tag.toString()
//
//        if (name.equals("2",)) {
//            //write your code here
//            showBottomSheetDialog()
//        }
//
//        return true
//    }


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


        viewModel.vehicle.observe(this){
            //binding.toolbarText.setText(it.grp_name)

        }

        viewModel.packetParser.observe(this){

                lat= it.lat.toDouble()
                lng=it.lng.toDouble()
            binding.toolbarText.setText(it.sim_no)

        }

    }
    private fun showBottomSheetDialogForMapHistoryViewTypeInfo() {

        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })



        bottomSheetDialog.setContentView(R.layout.map_history_view_type_sub_sheet_info)
        val btnCloseBottomSheet = bottomSheetDialog.findViewById<TextView>(R.id.btn_search_bottom)
        // val resetOdometer = bottomSheetDialog.findViewById<LinearLayout>(R.id.bottom_sheet_settings_layout_reset_odometer)

        //close button
        btnCloseBottomSheet?.setOnClickListener {
            bottomSheetDialog.cancel()
        }


        bottomSheetDialog.show()

    }
    private fun showBottomSheetDialogForMapHistoryViewTypeOne() {

        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })
//        googleMap?.clear()
//        markingOptions = MarkerOptions().position(LatLng())
//        googleMap?.addMarker(markingOptions)




        bottomSheetDialog.setContentView(R.layout.map_history_sub_sheet_one)
        val btnCloseBottomSheetTop = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_top)
        val infoIcon = bottomSheetDialog.findViewById<ImageView>(R.id.map_view_type_info_icon)
//        val infoIcon = bottomSheetDialog.findViewById<ImageView>(R.id.map_view_type_info_icon)

        //close button
        btnCloseBottomSheetTop?.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        //info button
        infoIcon?.setOnClickListener {

           showBottomSheetDialogForMapHistoryViewTypeInfo()

        }

//        val btnCloseBottomSheetBottom = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_bottom)
//
//        //close button
//        btnCloseBottomSheetBottom?.setOnClickListener {
//            bottomSheetDialog.cancel()
//        }

        bottomSheetDialog.show()

    }
    private fun showBottomSheetDialogForMapHistory() {


        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })



        bottomSheetDialog.setContentView(R.layout.map_history_bottom_sheet)
        val dropdownViewType = bottomSheetDialog.findViewById<Spinner>(R.id.simpleSpinnerViewType)
        val itemsViewType = arrayOf("Replay")
        val adapterViewType = ArrayAdapter(this, R.layout.spinner_item, itemsViewType)
        if (dropdownViewType != null) {
            dropdownViewType.adapter = adapterViewType
        }
        val dropdownPeriodType = bottomSheetDialog.findViewById<Spinner>(R.id.simpleSpinnerPeriodType)
        val itemsPeriodType = arrayOf("Today", "Weekly", "Monthly","Custom Date")
        val adapterPeriodType = ArrayAdapter(this, R.layout.spinner_item, itemsPeriodType)
        if (dropdownPeriodType != null) {
            dropdownPeriodType.adapter = adapterPeriodType
        }
        val btnCloseBottomSheetTop = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_top)
        val btnSearchBottomSheet = bottomSheetDialog.findViewById<TextView>(R.id.btn_search_bottom_map)
        val viewType = bottomSheetDialog.findViewById<LinearLayout>(R.id.layout_map_history_view_type)
        val  edtTextStartDateSelect  = bottomSheetDialog.findViewById<EditText>(R.id.startDate)
        val  edtTextEndDateSelect  = bottomSheetDialog.findViewById<EditText>(R.id.endDate)

        // val resetOdometer = bottomSheetDialog.findViewById<LinearLayout>(R.id.bottom_sheet_settings_layout_reset_odometer)
//        btnStartDateSelect?.setOnClickListener {
//
//        }
        edtTextStartDateSelect?.transformIntoDatePicker(this, "MM/dd/yyyy")
        edtTextEndDateSelect?.transformIntoDatePicker(this, "MM/dd/yyyy")



        //Search button
        btnSearchBottomSheet?.setOnClickListener {
            bottomSheetDialog.cancel()
            val intent = Intent(this, HistoryReplayActivity::class.java)

            startActivity(intent)

        }
        //view type button

//        viewType?.setOnClickListener {
//            bottomSheetDialog.cancel()
//            val intent = Intent(this, HistoryReplayActivity::class.java)
//
//            startActivity(intent)
//
////            showBottomSheetDialogForMapHistoryViewTypeOne()
//        }
        //close button
        btnCloseBottomSheetTop?.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        //close button and open next sheet
//        resetOdometer?.setOnClickListener {
//            bottomSheetDialog.cancel()
//            showSubBottomSheetDialogForMapSettingsResetOdometerOne()
//
//        }

//        val btnCloseBottomSheetBottom = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_bottom)
//
//        //close button
//        btnCloseBottomSheetBottom?.setOnClickListener {
//            bottomSheetDialog.cancel()
//        }

        bottomSheetDialog.show()

    }
    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }


    private fun showBottomSheetDialogForMapControlOnOffSendTakePicture() {

        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })



        bottomSheetDialog.setContentView(R.layout.map_control_subsheet_send_on_off_subsheet_send_take_picture)
        val btnCloseBottomSheetTop = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_top)
       // val resetOdometer = bottomSheetDialog.findViewById<LinearLayout>(R.id.bottom_sheet_settings_layout_reset_odometer)

        //close button
        btnCloseBottomSheetTop?.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        //close button and open next sheet
//        resetOdometer?.setOnClickListener {
//            bottomSheetDialog.cancel()
//            showSubBottomSheetDialogForMapSettingsResetOdometerOne()
//
//        }

        val btnCloseBottomSheetBottom = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_bottom)

        //close button
        btnCloseBottomSheetBottom?.setOnClickListener {
            bottomSheetDialog.cancel()
        }

        bottomSheetDialog.show()

    }
    private fun showBottomSheetDialogForMapControlSendOutPut() {

        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })






        bottomSheetDialog.setContentView(R.layout.map_control_subsheet_send_on_off_output)
        val btnCloseBottomSheetTop = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_top)
        val btnOnOff = bottomSheetDialog.findViewById<TextView>(R.id.btn_send_bottom)

        //close button
        btnCloseBottomSheetTop?.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        //close button and open next sheet
        btnOnOff?.setOnClickListener {
            bottomSheetDialog.cancel()
            showBottomSheetDialogForMapControlOnOffSendTakePicture()

        }

        val btnCloseBottomSheetBottom = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_bottom)

        //close button
        btnCloseBottomSheetBottom?.setOnClickListener {
            bottomSheetDialog.cancel()
        }

        bottomSheetDialog.show()

    }
    private fun showSubBottomSheetDialogForMapSettingsResetOdometerOne() {

        subBottomSheetDialogForMapSettingsResetOdometerOne = BottomSheetDialog(this)

        subBottomSheetDialogForMapSettingsResetOdometerOne.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })


        subBottomSheetDialogForMapSettingsResetOdometerOne.setContentView(R.layout.map_settings_sub_sheet_odometer_one)
        val btnCloseBottomSheetTop = subBottomSheetDialogForMapSettingsResetOdometerOne.findViewById<TextView>(R.id.btn_cancel_top)
        val btnSendBottomSheetBottom = subBottomSheetDialogForMapSettingsResetOdometerOne.findViewById<TextView>(R.id.btn_send_bottom)


        btnCloseBottomSheetTop?.setOnClickListener {
            subBottomSheetDialogForMapSettingsResetOdometerOne.cancel()
        }


        btnSendBottomSheetBottom?.setOnClickListener {
            subBottomSheetDialogForMapSettingsResetOdometerOne.cancel()
            showSubBottomSheetDialogForMapSettingsResetOdometerTwo()
        }


        val btnCloseBottomSheetBottom = subBottomSheetDialogForMapSettingsResetOdometerOne.findViewById<TextView>(R.id.btn_cancel_bottom)


        btnCloseBottomSheetBottom?.setOnClickListener {
            subBottomSheetDialogForMapSettingsResetOdometerOne.cancel()
        }

        subBottomSheetDialogForMapSettingsResetOdometerOne.show()


    }
    private fun showSubBottomSheetDialogForMapSettingsResetOdometerTwo() {


        subBottomSheetDialogForMapSettingsResetOdometerTwo= BottomSheetDialog(this)
        subBottomSheetDialogForMapSettingsResetOdometerTwo.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })


        subBottomSheetDialogForMapSettingsResetOdometerTwo.setContentView(R.layout.map_settings_sub_sheet_odometer_two)
        val btnCloseBottomSheetTop = subBottomSheetDialogForMapSettingsResetOdometerTwo.findViewById<TextView>(R.id.btn_cancel_top)


        btnCloseBottomSheetTop?.setOnClickListener {
            subBottomSheetDialogForMapSettingsResetOdometerTwo.cancel()
        }

        val btnCloseBottomSheetBottom = subBottomSheetDialogForMapSettingsResetOdometerTwo.findViewById<TextView>(R.id.btn_cancel_bottom)


        btnCloseBottomSheetBottom?.setOnClickListener {
            subBottomSheetDialogForMapSettingsResetOdometerTwo.cancel()
        }

        subBottomSheetDialogForMapSettingsResetOdometerTwo.show()


    }
    private fun showBottomSheetDialogForMapSettings() {

        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })






        bottomSheetDialog.setContentView(R.layout.map_settings_bottom_sheet)
        val btnCloseBottomSheetTop = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_top)
        val resetOdometer = bottomSheetDialog.findViewById<LinearLayout>(R.id.bottom_sheet_settings_layout_reset_odometer)

        //close button
        btnCloseBottomSheetTop?.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        //close button and open next sheet
        resetOdometer?.setOnClickListener {
            bottomSheetDialog.cancel()
            showSubBottomSheetDialogForMapSettingsResetOdometerOne()

        }

        val btnCloseBottomSheetBottom = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_bottom)

        //close button
        btnCloseBottomSheetBottom?.setOnClickListener {
            bottomSheetDialog.cancel()
        }

        bottomSheetDialog.show()

    }
    private fun showBottomSheetDialogForMapControls() {

        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })


        bottomSheetDialog.setContentView(R.layout.map_controls_bottom_sheet)
        val btnCloseBottomSheet = bottomSheetDialog.findViewById<Button>(R.id.btn_close_bottom_sheet)
        val btnOnOff = bottomSheetDialog.findViewById<LinearLayout>(R.id.layout_output_on_off)

        //close button
        btnCloseBottomSheet?.setOnClickListener {
            bottomSheetDialog.cancel()
        }

        btnOnOff?.setOnClickListener {

            showBottomSheetDialogForMapControlSendOutPut()
        }

        bottomSheetDialog.show()


    }
    private fun showBottomSheetDialogForVehicleInfo() {

        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })




        bottomSheetDialog.setContentView(R.layout.bottom_sheet_for_map_screen_one)
        val showLessViewButton = bottomSheetDialog.findViewById<Button>(R.id.show_less_button)
        val showMoreViewButton = bottomSheetDialog.findViewById<Button>(R.id.show_more_button)
        val bottomPortionLayout = bottomSheetDialog.findViewById<LinearLayout>(R.id.bottom_portion_layout)
        val upperPortionLayout = bottomSheetDialog.findViewById<LinearLayout>(R.id.upper_layout_bottom_sheet)

        //TextViews

        val deviceName = bottomSheetDialog.findViewById<TextView>(R.id.tv_device_name)
        val gps1 = bottomSheetDialog.findViewById<TextView>(R.id.tv_gps_one)
        val hdop = bottomSheetDialog.findViewById<TextView>(R.id.tv_hdop)
        val gsm= bottomSheetDialog.findViewById<TextView>(R.id.tv_gsm)
        val vdop = bottomSheetDialog.findViewById<TextView>(R.id.tv_vdop)
        val gps2 = bottomSheetDialog.findViewById<TextView>(R.id.tv_gps_two)
        val int = bottomSheetDialog.findViewById<TextView>(R.id.tv_int)
        val exit = bottomSheetDialog.findViewById<TextView>(R.id.tv_exit)

        val speed = bottomSheetDialog.findViewById<TextView>(R.id.tv_speed)
        val rpm = bottomSheetDialog.findViewById<TextView>(R.id.tv_rpm)
        val mileage = bottomSheetDialog.findViewById<TextView>(R.id.tv_mileage)

        val coordinates = bottomSheetDialog.findViewById<TextView>(R.id.tv_coordinates)
        val geoFence = bottomSheetDialog.findViewById<TextView>(R.id.tv_geofence)
        val routeStatus = bottomSheetDialog.findViewById<TextView>(R.id.tv_route_status)
        val driverName1 = bottomSheetDialog.findViewById<TextView>(R.id.tv_driver_name_one)

        val deviceInfo = bottomSheetDialog.findViewById<TextView>(R.id.tv_device_info)
        val deviceNumber = bottomSheetDialog.findViewById<TextView>(R.id.tv_device_number)
        val deviceType = bottomSheetDialog.findViewById<TextView>(R.id.tv_device_type)
        val deviceModel = bottomSheetDialog.findViewById<TextView>(R.id.tv_device_model)
        val driverName2 = bottomSheetDialog.findViewById<TextView>(R.id.tv_driver_name_two)
        val simNumber = bottomSheetDialog.findViewById<TextView>(R.id.tv_sim_number)
        val managementNumber = bottomSheetDialog.findViewById<TextView>(R.id.tv_management_number)
        val date = bottomSheetDialog.findViewById<TextView>(R.id.tv_date)




        //calling vehicle data
        viewModel.getVehicle()
        //calling packet parser
        viewModel.getPacketParser()


        viewModel.vehicle.observe(this){
            binding.toolbarText.setText(it.grp_name)

        }

        viewModel.packetParser.observe(this){

            if(it.device_id !=null )
            { deviceName?.setText(it.device_id)}



            if(it.gps_satelite !=null)
            { gps1?.setText(it.gps_satelite) }

            if(it.h_v_dop !=null)
            { hdop?.setText(it.h_v_dop) }

            if(it.gsm_signal !=null)
            { gsm?.setText(it.gsm_signal) }

            if(it.h_v_dop !=null)
            { vdop?.setText(it.h_v_dop) }

            if(it.gps_status !=null)
            { gps2?.setText(it.gps_status) }

            if(it.int_bat_voltage !=null)
            { int?.setText(it.int_bat_voltage) }

            if(it.ext_bat_voltage !=null)
            { exit?.setText(it.ext_bat_voltage) }


            if(it.speed !=null)
            { speed?.setText(it.speed+" km/h") }

            if(it.rpm !=null)
            { rpm?.setText(it.rpm+" R/min") }

            if(it.mileage_last_noted !=null)
            { mileage?.setText(it.mileage_last_noted) }

            if(it.lat !=null && it.lng !=null)
            { coordinates?.setText(it.lat+","+it.lng) }

            if(it.geo_status !=null)
            { geoFence?.setText(it.geo_status) }

//            if(it.rout !=null)
//            { geoFence?.setText(it.geo_status) }

            if(it.driver !=null)
            { driverName1?.setText(it.driver) }

//            if(it.deviceName !=null)
//            { deviceName?.setText(it.deviceName) }

            if(it.device_id !=null)
            { deviceNumber?.setText(it.device_id) }

            if(it.dev_type_id !=null)
            { deviceType?.setText(it.dev_type_id) }

            if(it.dev_model_id !=null)
            { deviceModel?.setText(it.dev_model_id.toString()) }

            if(it.driver !=null)
            { driverName2?.setText(it.driver) }

            if(it.sim_no !=null)
            { simNumber?.setText(it.sim_no) }

            if(it.mgt_sim_no !=null)
            { managementNumber?.setText(it.mgt_sim_no) }

            if(it.rec_datetime !=null)
            { date?.setText(it.rec_datetime) }

        }


        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.setDuration(800)
        transition.addTarget(R.id.bottom_portion_layout)


        showLessViewButton?.setOnClickListener {


            TransitionManager.beginDelayedTransition(bottomSheetDialog.findViewById(R.id.bottom_portion_layout), transition)


            bottomPortionLayout?.visibility = View.GONE
            showLessViewButton?.visibility = View.GONE
            showMoreViewButton?.visibility = View.VISIBLE

        }
        showMoreViewButton?.setOnClickListener {

            TransitionManager.beginDelayedTransition(bottomSheetDialog.findViewById(R.id.bottom_portion_layout), transition)



            bottomPortionLayout?.visibility = View.VISIBLE
            showMoreViewButton?.visibility = View.GONE
            showLessViewButton?.visibility = View.VISIBLE

        }


        bottomSheetDialog.window?.transitionBackgroundFadeDuration=500
        bottomSheetDialog.dismissWithAnimation

        bottomSheetDialog.show()
    }

    override fun getViewModel() = MapViewModel::class.java

    override fun getViewBinding()=  ActivityMapScreenBinding.inflate(layoutInflater)

    override fun getRepository()= MapRepository()

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

                showBottomSheetDialogForMapControls()

            }
            R.id.action_settings -> {

                showBottomSheetDialogForMapSettings()
            }
            R.id.action_history -> {

                showBottomSheetDialogForMapHistory()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_screen_menu, menu)

        // return true so that the menu pop up is opened
        return true
    }



}




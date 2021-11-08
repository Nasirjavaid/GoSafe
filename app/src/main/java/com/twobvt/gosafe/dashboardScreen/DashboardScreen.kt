package com.twobvt.gosafe.dashboardScreen


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.faskn.lib.Slice
import com.faskn.lib.buildChart
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.twobvt.gosafe.R
import com.twobvt.gosafe.config.TinyDB
import com.twobvt.gosafe.config.deleteAccessToken
import com.twobvt.gosafe.databinding.ActivityDashboardScreenBinding
import com.twobvt.gosafe.geofenceList.uiGeofenceList.uigeofencelist.GeoFenceListActivity
import com.twobvt.gosafe.login.ui.LoginScreen
import com.twobvt.gosafe.systemIndicatorScreen.ui.SystemIndicatorScreen
import com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen.VehiclesAndAssetsScreen
import kotlinx.android.synthetic.main.bottom_sheet_geofence.*
import kotlinx.android.synthetic.main.layout_dashboard_home_view.*
import kotlin.random.Random
import com.faskn.lib.PieChart as faskn


//import com.github.mikephil.charting.charts.PieChart



class DashboardScreen : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener ,
    OnChartValueSelectedListener {


    private lateinit var binding: ActivityDashboardScreenBinding
    private lateinit var tinyDB : TinyDB
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: Toolbar

    private lateinit var mpPieChart: PieChart
    private val tvX: TextView? = null
    private  var tvY:TextView? = null

    var tvSystemIndicatorItemCount: TextView? = null
    var systemIndicatorItemCount = 10

    private lateinit var titleGf : String
    private lateinit var typeGf : String
    private lateinit var coordinateGf : String
    private lateinit var differenceGf : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tiny DB -> shared preferences
        tinyDB = TinyDB(applicationContext)

        mpPieChart = binding.appBarDashboardScreen.chart1


        //Setting up toolbar icons and drawer items
        setupToolbarIconsAndNaveView()
        // all pieCharts
        allPieCharts()
        // bottom list item onVehiclesLitItemClicked function
        onVehiclesLitItemClicked(applicationContext)
//        onGeoFenceListItemClick(applicationContext)
        onGeoFenceItemClick(applicationContext)



    }


    private fun setupToolbarIconsAndNaveView()
    {
// disable the night mode for the whole application
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        toolbar = binding.appBarDashboardScreen.customToolbarLayout.customToolbar
        toolbar.setTitle("")


        setSupportActionBar(toolbar)
        var  drawer = binding.drawerLayout


        drawerToggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerToggle.setDrawerIndicatorEnabled(false);

        drawerToggle.setHomeAsUpIndicator(R.drawable.hamburger_white)
        drawer.addDrawerListener(drawerToggle)

        drawerToggle.syncState()


        drawerToggle.setToolbarNavigationClickListener {

            // If the navigation drawer is not open then open it, if its already open then close it.
            // If the navigation drawer is not open then open it, if its already open then close it.
            if (!drawer.isDrawerOpen(GravityCompat.START))
                drawer.openDrawer(GravityCompat.START)
            else drawer.closeDrawer(
                GravityCompat.END)
        }

        var navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var itemId =item.itemId

        when(itemId){

           R.id.nav_logout -> {

               logoutUser()
           }

        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_alert -> {

                startActivity(Intent(this, SystemIndicatorScreen::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_screen, menu)
        val menuItem = menu.findItem(R.id.action_alert)
        val actionView = menuItem.actionView
        tvSystemIndicatorItemCount = actionView.findViewById<TextView>(R.id.cart_badge)
        setupBadge()
        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
        return true
    }


    private fun setupBadge() {
        if (tvSystemIndicatorItemCount != null) {
            if (systemIndicatorItemCount === 0) {
                if (tvSystemIndicatorItemCount!!.visibility!== View.GONE) {
                    tvSystemIndicatorItemCount!!.visibility = View.GONE
                }
            } else {
                tvSystemIndicatorItemCount!!.text = Math.min(systemIndicatorItemCount, 99).toString()
                if (tvSystemIndicatorItemCount!!.visibility !== View.VISIBLE) {
                    tvSystemIndicatorItemCount!!.visibility = View.VISIBLE
                }
            }
        }
    }


    private  fun allPieCharts(){

        mpPieChartMain()
       /// mainPieChart()
        alarmPieChart()
        movingPieChart()
        parkedPieChart()
        idlePieChart()
        offlinePieChart()
        totalPieChart()
    }


    private fun mpPieChartMain(){

        mpPieChart.description.isEnabled = false
        mpPieChart.holeRadius = 75f
        mpPieChart.transparentCircleRadius = 0f
        mpPieChart.setHoleColor(Color.TRANSPARENT)
        mpPieChart.legend.isEnabled = false
        mpPieChart.isRotationEnabled = false
        mpPieChart.setTouchEnabled(true)
    //   mpPieChart.maxAngle = 270f
//        mpPieChart.rotation = -135f
//        mpPieChart.animateX(400)
        mpPieChart.setDrawRoundedSlices(true)

        mpPieChart.setUsePercentValues(true)
        mpPieChart.description.isEnabled = false
        mpPieChart.setExtraOffsets(5.0f, 10.0f, 5.0f, 5.0f)

        mpPieChart.dragDecelerationFrictionCoef = 0.95f

       // mpPieChart.setCenterTextTypeface(tfLight)
        //mpPieChart.centerText = generateCenterSpannableText()

        mpPieChart.isDrawHoleEnabled = true
        mpPieChart.setHoleColor(Color.WHITE)

        mpPieChart.setTransparentCircleColor(Color.WHITE)
        mpPieChart.setTransparentCircleAlpha(110)

        mpPieChart.holeRadius = 74f
        mpPieChart.transparentCircleRadius = 61f

        mpPieChart.setDrawCenterText(true)

        mpPieChart.setRotationAngle(0f)
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        mpPieChart.isRotationEnabled = true
        mpPieChart.isHighlightPerTapEnabled = true
        mpPieChart.circleBox.isEmpty

        // add a selection listener
        mpPieChart.setOnChartValueSelectedListener(this)

        mpPieChart.animateY(2500, Easing.EaseInOutQuad)
        mpPieChart.spin(2000, 0f, 360F, Easing.EaseInOutQuad)

       // mpPieChart.spin(2000, 0f, 360f, Easing.EaseInOutQuad);

        mpPieChart.getLegend().setEnabled(false)
       // val l = mpPieChart.legend
//        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        l.orientation = Legend.LegendOrientation.VERTICAL
//        l.setDrawInside(false)
//        l.xEntrySpace = 7f
//        l.yEntrySpace = 0f
//        l.yOffset = 0f

        // entry label styling

        // entry label styling
        mpPieChart.setEntryLabelColor(Color.WHITE)
        //mpPieChart.setEntryLabelTypeface(tfRegular)
        mpPieChart.setEntryLabelTextSize(12f)

        // setting data to pie chart
        setData(4,100f)

    }

    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }

    private fun setData(count: Int, range: Float) {
        val entries: ArrayList<PieEntry> = ArrayList()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(PieEntry((Math.random() * range + range / 5).toFloat(),
                "String",
                resources.getDrawable(R.drawable.cell_icon)))
        }
        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f

        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors: ArrayList<Int> = ArrayList()

        val MY_COLORS =
            intArrayOf(
                ContextCompat.getColor(applicationContext, R.color.red),
                ContextCompat.getColor(applicationContext, R.color.green),
                ContextCompat.getColor(applicationContext, R.color.yellow),
                ContextCompat.getColor(applicationContext, R.color.blue),


               )


        for (c in MY_COLORS) colors.add(c)
        dataSet.colors = colors

        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
       // data.setValueTypeface(tfLight)
        mpPieChart.setData(data)


        // undo all highlights
        mpPieChart.highlightValues(null)
        mpPieChart.invalidate()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
            "Value: " + e.getY() + ", index: "
                    + ", DataSet index: " );
    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


//    fun mainPieChart() {
//
//        val pieChart = PieChart(
//            slices = provideSlices(), clickListener = null, sliceStartPoint = 0f, sliceWidth = 100f
//        ).build()
//
//        chart.setPieChart(pieChart)
//        // Kotlin DSL example
//        val pieChartDSL = buildChart {
//            slices { provideSlices() }
//            sliceWidth { 35f }
//            sliceStartPoint { 0f }
//
//            clickListener { angle, index ->
//
//            }
//        }
//
//        chart.setPieChart(pieChartDSL)
//
//    }

    private fun provideSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(1000, 3000).toFloat(),
                R.color.blue,
                "Google"
            ),
            Slice(

                Random.nextInt(1000, 2000).toFloat(),
                R.color.red,

                "Facebook"

            ),
            Slice(
                Random.nextInt(1000, 5000).toFloat(),
                R.color.yellow,
                "Twitter"
            ),
            Slice(
                Random.nextInt(1000, 10000).toFloat(),
                R.color.green,
                "Other"
            )
        )
    }

    private fun alarmPieChart() {

        val pieChart = faskn(
            slices = alarmPieChartSlices(), clickListener = null, sliceStartPoint = 3f, sliceWidth = 8f,
        ).build()

        pie_chart_alarm.setPieChart(pieChart)
        // Kotlin DSL example
        val pieChartDSL = buildChart {
            slices { alarmPieChartSlices() }
            sliceWidth { 8f }
            sliceStartPoint { 3f }

            clickListener { angle, index ->

            }
        }

        pie_chart_alarm.setPieChart(pieChartDSL)
    }

    private fun alarmPieChartSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.red,
                "Google"
            ),
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.white,
                "Google"
            ),
            )
    }

    private fun movingPieChart() {

        val pieChart = faskn(
            slices = movingPieChartSlices(), clickListener = null, sliceStartPoint = 3f, sliceWidth = 8f,
        ).build()

        pie_chart_moving.setPieChart(pieChart)
        // Kotlin DSL example
        val pieChartDSL = buildChart {
            slices { movingPieChartSlices() }
            sliceWidth { 8f }
            sliceStartPoint { 3f }

            clickListener { angle, index ->

            }
        }

        pie_chart_moving.setPieChart(pieChartDSL)

    }

    private fun movingPieChartSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.green,
                "Google"


            ),
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.white,
                "Google"
            ),

            )
    }

    private fun parkedPieChart() {

        val pieChart = faskn(
            slices = parkedPieChartSlices(), clickListener = null, sliceStartPoint = 3f, sliceWidth = 8f,
        ).build()

        pie_chart_parked.setPieChart(pieChart)
        // Kotlin DSL example
        val pieChartDSL = buildChart {
            slices { parkedPieChartSlices() }
            sliceWidth { 8f }
            sliceStartPoint { 3f }


            clickListener { angle, index ->

            }
        }

        pie_chart_parked.setPieChart(pieChartDSL)

    }

    private fun parkedPieChartSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.yellow,
                "Google"


            ),
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.white,
                "Google"
            ),

            )
    }

    private fun idlePieChart() {

        val pieChart = faskn(
            slices = idlePieChartSlices(), clickListener = null, sliceStartPoint = 3f, sliceWidth = 8f,
        ).build()

        pie_chart_idle.setPieChart(pieChart)
        // Kotlin DSL example
        val pieChartDSL = buildChart {
            slices { idlePieChartSlices() }
            sliceWidth { 8f }
            sliceStartPoint { 3f }


            clickListener { angle, index ->

            }
        }

        pie_chart_idle.setPieChart(pieChartDSL)

    }

    private fun idlePieChartSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.blue,
                "Google"


            ),
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.white,
                "Google"
            ),

            )
    }

    private fun offlinePieChart() {

        val pieChart = faskn(
            slices = offlinePieChartSlices(), clickListener = null, sliceStartPoint = 3f, sliceWidth = 8f,
        ).build()

        pie_chart_offline.setPieChart(pieChart)
        // Kotlin DSL example
        val pieChartDSL = buildChart {
            slices { offlinePieChartSlices() }
            sliceWidth { 8f }
            sliceStartPoint { 3f }


            clickListener { angle, index ->

            }
        }

        pie_chart_offline.setPieChart(pieChartDSL)

    }

    private fun offlinePieChartSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.grey,
                "Google"


            ),
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.white,
                "Google"
            ),

            )
    }

    private fun totalPieChart() {

        val pieChart = faskn(
            slices = totalPieChartSlices(), clickListener = null, sliceStartPoint = 3f, sliceWidth = 8f,
        ).build()

        pie_chart_total.setPieChart(pieChart)
        // Kotlin DSL example
        val pieChartDSL = buildChart {
            slices { totalPieChartSlices() }
            sliceWidth { 8f }
            sliceStartPoint { 3f }


            clickListener { angle, index ->

            }
        }

        pie_chart_total.setPieChart(pieChartDSL)

    }

    private fun totalPieChartSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.black,
                "Google"


            ),
            Slice(
                Random.nextInt(0, 1000).toFloat(),
                R.color.white,
                "Google"
            ),

            )
    }

    private fun onVehiclesLitItemClicked(  context: Context ){

        binding.appBarDashboardScreen.vehiclesCardViewListItem.setOnClickListener{

            startActivity(Intent(context, VehiclesAndAssetsScreen::class.java))

        }
    }
    private fun onGeoFenceItemClick(context:Context){

        binding.appBarDashboardScreen.geoFenceCardClick.setOnClickListener {
            showBottomSheetGeofence()
            print("card clicked of geofence")
        }

    }

    private fun logoutUser() {


            deleteAccessToken(applicationContext,"")
            finish()
            startActivity(Intent(this, LoginScreen::class.java))


    }

    private fun showBottomSheetGeofence() {

        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setOnShowListener(DialogInterface.OnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
                ?: return@OnShowListener

            bottomSheet.background = null

        })



        bottomSheetDialog.setContentView(R.layout.bottom_sheet_geofence)
        val dropdownCountry = bottomSheetDialog.findViewById<Spinner>(R.id.simpleSpinnerCountry)
        val itemsCountry = arrayOf("Pakistan", "Bangladesh")
        val adapterCountry = ArrayAdapter(this, R.layout.spinner_item, itemsCountry)
        if (dropdownCountry != null) {
            dropdownCountry.adapter = adapterCountry
        }
        val dropdownCity = bottomSheetDialog.findViewById<Spinner>(R.id.simpleSpinnerCity)
        val itemsCity = arrayOf("Lahore", "Islamabad", "Karachi")
        val adapterCity = ArrayAdapter(this, R.layout.spinner_item, itemsCity)
        if (dropdownCity != null) {
            dropdownCity.adapter = adapterCity
        }

        val btnCloseBottomSheetTop = bottomSheetDialog.findViewById<TextView>(R.id.btn_cancel_top)
        val viewType = bottomSheetDialog.findViewById<LinearLayout>(R.id.layout_map_history_view_type)
        val btnGoAhead = bottomSheetDialog.findViewById<TextView>(R.id.btn_search_bottom)
        // val resetOdometer = bottomSheetDialog.findViewById<LinearLayout>(R.id.bottom_sheet_settings_layout_reset_odometer)




        //view type button
        viewType?.setOnClickListener {
            bottomSheetDialog.cancel()
//            showBottomSheetDialogForMapHistoryViewTypeOne()
        }
        //close button
        btnCloseBottomSheetTop?.setOnClickListener {
            print("tapiingggggggggggggggggggggggggggggg")
            bottomSheetDialog.cancel()
        }
        btnGoAhead?.setOnClickListener {
            titleGf = bottomSheetDialog.title.text.toString()
            typeGf = bottomSheetDialog.type.text.toString()
            coordinateGf = bottomSheetDialog.coordinate.text.toString()
                differenceGf = bottomSheetDialog.difference.text.toString()
            if(titleGf.isEmpty()){
                Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show()
            }
            when{
                titleGf.isEmpty() -> {
                    Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show()
                }
                typeGf.isEmpty() -> {
                    Toast.makeText(this, "Please Enter Type", Toast.LENGTH_SHORT).show()
                }
                coordinateGf.isEmpty() -> {
                    Toast.makeText(this, "Please Enter Coordinate", Toast.LENGTH_SHORT).show()
                }
                differenceGf.isEmpty() -> {
                    Toast.makeText(this, "Please Enter Difference", Toast.LENGTH_SHORT).show()
                }
                titleGf.length > 20 -> {
                    Toast.makeText(this, "Title Shouldn't be longer than 20 characters", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    startActivity(Intent(this, GeoFenceListActivity::class.java))
                }

            }


//            print("tapiingggggggggggggggggggggggggggggg")

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




}
package com.twobvt.gosafe.geofenceList.uiGeofenceList.uigeofencelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.twobvt.gosafe.R
import com.twobvt.gosafe.base.BaseActivity
import com.twobvt.gosafe.config.TinyDB
import com.twobvt.gosafe.config.getAccessToken
import com.twobvt.gosafe.config.handleApiErrors
import com.twobvt.gosafe.databinding.ActivityGeoFenceListBinding
import com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data
import com.twobvt.gosafe.login.authRepository.GeofenceRepo
import com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen.VehicleAdaptor
import com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen.VehiclesFragment
import com.twobvt.gosafe.vehiclesAndAssets.vaApi.geofenceApi
import com.twobvt.gosafe.geofenceList.uiGeofenceList.geoFenceViewModel.GeoFenceViewModel
import com.twobvt.gosafe.geofenceList.uiGeofenceList.response.GeoFence
import com.twobvt.gosafe.network.Resource

class GeoFenceListActivity : BaseActivity<GeoFenceViewModel, ActivityGeoFenceListBinding, GeofenceRepo>(),
    AdapterView.OnItemSelectedListener , View.OnClickListener{

    private lateinit var tinyDB: TinyDB
    private lateinit var geofenceAdapter: GeoFenceAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.getGeoFenceTreeList()
        viewModel.geofenceResponse.observe(this, Observer {
            print("Here is response")
            print(it)
            when (it) {

                is Resource.Loading -> {
                    println("Loading.................................")
                }
                is Resource.Success -> {
                    setUpViews(it.value.data)
                    println("List size from View model ${it.value.data}")
                    //println("Success $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")

                    //this function is replacing thr initial view


                }

                is Resource.Failure -> {
                    handleApiErrors(it)
                }

            }
//




        })
//        VehiclesFragment()


//        setContentView(R.layout.activity_geo_fence_list)
    }
    private fun setUpViews(dataList: List<com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data>){

        binding.geofenceRecyclerView.layoutManager = LinearLayoutManager(baseContext,
            LinearLayoutManager.VERTICAL,false)
        geofenceAdapter = GeoFenceAdapter()
        binding.geofenceRecyclerView.adapter = geofenceAdapter

        renderGeofenceTreeList(dataList)
    }
    private fun renderGeofenceTreeList(dataList: List<com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data>) {
        geofenceAdapter.addData(dataList)
        geofenceAdapter.notifyDataSetChanged()
    }

    override fun getViewModel() = GeoFenceViewModel::class.java

    override fun getViewBinding() = ActivityGeoFenceListBinding.inflate(layoutInflater)

    override fun getRepository(): GeofenceRepo {
        lateinit var api: geofenceApi

        try {
            // tiny DB -> shared preferences
            tinyDB = TinyDB(applicationContext)

            var authToken: String = getAccessToken(applicationContext)

            api = remoteDataSource.buildApi(geofenceApi::class.java, authToken)

            return GeofenceRepo(api)
        } catch (e: NullPointerException) {
            println("Following exception occurred $e")
        } finally {

            return GeofenceRepo(api)
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }



}
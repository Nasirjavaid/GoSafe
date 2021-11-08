package com.twobvt.gosafe.systemIndicatorScreen.ui
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.twobvt.gosafe.base.BaseActivity
import com.twobvt.gosafe.config.handleApiErrors
import com.twobvt.gosafe.databinding.ActivitySytemIndicatorScreenBinding
import com.twobvt.gosafe.network.Resource
import com.twobvt.gosafe.systemIndicatorScreen.indicatorApi.IndicatorApi
import com.twobvt.gosafe.systemIndicatorScreen.indicatorRepository.IndicatorRepository
import com.twobvt.gosafe.systemIndicatorScreen.indicatorResponses.DummyModel
import com.twobvt.gosafe.systemIndicatorScreen.indicatorViewModel.IndicatorViewModel


class SystemIndicatorScreen : BaseActivity<IndicatorViewModel,ActivitySytemIndicatorScreenBinding,IndicatorRepository>(){


    private lateinit var indicatorAdapter: IndicatorAdapter
    private lateinit var dummyModel : DummyModel

    lateinit var dummyList : ArrayList<DummyModel>
    lateinit var toolbar: Toolbar



    override fun onCreate(savedInstanceState: Bundle?) {





        dummyList = ArrayList()

        super.onCreate(savedInstanceState)
// disable the night mode for the whole application
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            toolbar = binding.customToolbarSysIndicator





        setUpViews()
    }


    private fun callForSystemIndicatorData() {



        viewModel.getSystemIndicatorList()

                    //Calling login function from view model

                    viewModel.indicatorList .observe(this, Observer {

                       // binding.progressBarCircle.visible(it is Resource.Loading)
                        when (it) {

                            //if response is success then we will show List
                            is Resource.Success -> {

                               // progressBarCircle.visibility = View.INVISIBLE


                            }

                            is Resource.Failure -> {


                                handleApiErrors(it)
                                println("Login Failed")
                            }
                        }

                    })
                //1 seconds

            }

    private fun setUpViews() {

        binding.btnBack.setOnClickListener(){

            finish()
        }

        binding.systemIndicatorRecyclerview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        indicatorAdapter = IndicatorAdapter()
        binding.systemIndicatorRecyclerview.adapter = indicatorAdapter
        loaddata()

            // renderVehiclesTreeList(subMenuList)

    }

    private fun renderVehiclesTreeList(subMenu: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {
//        indicatorAdapter.addData(subMenu)
        indicatorAdapter.notifyDataSetChanged()
    }

    //For dummy Data purpose
    private fun loaddata() {
        dummyModel = DummyModel()

        for (i in 0..20) {

            dummyModel.alertName = "System Indicator #$i"
            dummyModel.alertDate = "12-03-2021"
            dummyModel.alertTime = "12:00 pm"
            dummyList!!.add(dummyModel)
        }
        indicatorAdapter.addData(dummyList)
        indicatorAdapter!!.notifyDataSetChanged()

    }



    override fun getViewModel() = IndicatorViewModel::class.java

    override fun getViewBinding() = ActivitySytemIndicatorScreenBinding.inflate(layoutInflater)

    override fun getRepository() = IndicatorRepository(remoteDataSource.buildApi(IndicatorApi::class.java))

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}
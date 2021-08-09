package com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.twobvt.gosafe.base.BaseActivity
import com.twobvt.gosafe.config.TinyDB
import com.twobvt.gosafe.config.getAccessToken
import com.twobvt.gosafe.config.handleApiErrors
import com.twobvt.gosafe.config.visible
import com.twobvt.gosafe.databinding.ActivityVehiclesAndAssetsScreenBinding
import com.twobvt.gosafe.network.Resource
import com.twobvt.gosafe.vehiclesAndAssets.vaApi.VaApi
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.AssetRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.VaRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.VehicleRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaViewModel.VaViewModel
import java.util.*


class VehiclesAndAssetsScreen :
    BaseActivity<VaViewModel, ActivityVehiclesAndAssetsScreenBinding, VaRepository>(),
    AdapterView.OnItemSelectedListener , View.OnClickListener{


    lateinit var searchView: SearchView
   // lateinit var listView: ListView
    lateinit var list: ArrayList<String>
    lateinit var adapter: ArrayAdapter<*>
    private lateinit var tinyDB: TinyDB

    private lateinit var vehiclesRepository: VehicleRepository
    private lateinit var assetRepository :AssetRepository

    var subMenuListVehicles: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = ArrayList()
    var subMenuListAssets: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Toolbar setup
        val toolbar: Toolbar = binding.newToolbar
        toolbar.setTitle("")
        setSupportActionBar(toolbar)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        vehiclesRepository = VehicleRepository()
        assetRepository =AssetRepository()

        //search view and listView setup
        searchView = binding.searchView
      //  listView = binding.listView



//
//        list = ArrayList()
//        list.add("Apple")
//        list.add("Banana")
//        list.add("Pineapple")
//        list.add("Orange")
//        list.add("Mango")
//        list.add("Grapes")
//        list.add("Lemon")
//        list.add("Melon")
//        list.add("Watermelon")
//        list.add("Papaya")
//        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
////        listView.adapter = adapter
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(query: String): Boolean {
//                if (list.contains(query)) {
//                    adapter.filter.filter(query)
//
//
//                } else {
//                    Toast.makeText(applicationContext, "No Match found", Toast.LENGTH_LONG).show()
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                adapter.filter.filter(newText)
//                return false
//            }
//        })


//
//       binding.segmentedButtonGroup.setOnClickedButtonPosition(object : OnClickedButtonPosition() {
//            fun onClickedButtonPosition(position: Int) {
//                Toast.makeText(this@MainActivity, "Clicked: $position", Toast.LENGTH_SHORT).show()
//            }
//        })
//        segmentedButtonGroup.setPosition(2, 0.toBoolean())


        //changing fragments
        changePositionButtonOnClick()

        // get list Of vehicles
        getVehiclesTreeList()

    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameContainer.id, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {

                if (isKeyboardShowing(this, binding.root)) {
                    searchView.clearFocus()
                } else {
                    onBackPressed()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    // change focus of search view
    private fun changeFocusFromSearchView() {

        if (isKeyboardShowing(this, binding.root)) {
            searchView.clearFocus()
        }
    }

    // hide the keyboard that is showing
    private fun isKeyboardShowing(context: Context, view: View): Boolean {
        return try {
            val keyboard: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0)
            // keyboard.isActive()


        } catch (ex: Exception) {
            Log.e("keyboardHide", "cannot hide keyboard", ex)
            false
        }
    }

    override fun getViewModel() = VaViewModel::class.java

    override fun getViewBinding() = ActivityVehiclesAndAssetsScreenBinding.inflate(layoutInflater)

    override fun getRepository(): VaRepository {
        lateinit var api: VaApi

        try {
            // tiny DB -> shared preferences
            tinyDB = TinyDB(applicationContext)

            var authToken: String = getAccessToken(applicationContext)

            api = remoteDataSource.buildApi(VaApi::class.java, authToken)

            return VaRepository(api)
        } catch (e: NullPointerException) {
            println("Following exception occurred $e")
        } finally {

            return VaRepository(api)
        }


    }

    private fun getVehiclesTreeList() {

        //Custom waiting
            //Calling login function from view model
            viewModel.getVehiclesTreeList()
            viewModel.vaResponse.observe(this, Observer {
                binding.vaProgressBarCircle.visible(it is Resource.Loading)

                when (it) {

                    is Resource.Loading -> {
                        println("Loading.................................")
                    }
                    is Resource.Success -> {

                        println("Success $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")

                        //this function is replacing thr initial view
                        replaceFragment(VehiclesFragment())

                        subMenuListVehicles =it.value[0].SubMenu
                        subMenuListAssets =it.value[1].SubMenu
                        //sending data to repositories
                        vehiclesRepository.sendVehiclesList(subMenuListVehicles)
                        assetRepository.sendAssetList(subMenuListAssets)
                        println("Success sublist size is  ${  it.value[0]}")

                    }

                    is Resource.Failure -> {
                        handleApiErrors(it)
                    }

                }

            })
    }

    private  fun changePositionButtonOnClick() {
        var position: Int = binding.segmentedButtonGroup.position

        binding.segmentedButtonGroup.setOnPositionChangedListener {

            when(it){

                0 -> {
                    replaceFragment(VehiclesFragment())
                }

                1 -> {
                    replaceFragment(AssetsFragment())}
            }
        }
//        position = ++position % 2
//
//        println("Position $position")


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")

        println("Positaisdjnvfiasdnfiawenf")
    }

}






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
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.text.toLowerCase
import androidx.core.view.get
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
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu
import com.twobvt.gosafe.vehiclesAndAssets.vaViewModel.VaViewModel
import java.util.*


class VehiclesAndAssetsScreen :
    BaseActivity<VaViewModel, ActivityVehiclesAndAssetsScreenBinding, VaRepository>(),
    AdapterView.OnItemSelectedListener , View.OnClickListener{


    lateinit var searchView: SearchView
   // lateinit var listView: ListView
    lateinit var list: ArrayList<String>
    lateinit var lista: List<SubMenu>
    lateinit var adapter: ArrayAdapter<*>
    private lateinit var tinyDB: TinyDB
    var listb : MutableList<SubMenu> = mutableListOf()
    var listc : MutableList<SubMenu> = mutableListOf()
    var fragment_number : Int = 0
    var searchText : String = ""

    private lateinit var vehiclesRepository: VehicleRepository
    private lateinit var assetRepository : AssetRepository
    var subMenuListVehicles : List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = ArrayList()
    var subMenuListAssets : List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = ArrayList()


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
        print("search query here ")
        print(searchView.query)




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
//        listView.adapter = adapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                print("query hereeeeeeeeeeee")
                print(query)


                    getVehiclesTreeList(query)



                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                getVehiclesTreeList(newText)
                return false
            }
        })


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
        getVehiclesTreeList("")

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

    private fun getVehiclesTreeList(string:String) {

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

                        subMenuListVehicles =it.value.data[0].SubMenu
                        subMenuListAssets =it.value.data[1].SubMenu
                        if(fragment_number == 0){
                            if(string == "" ){
                                print("without query")
                                listb = subMenuListVehicles.toMutableList()
                                listc = subMenuListAssets.toMutableList()
                                print("list sixe")
                                print(listb.size)
                                replaceFragment(VehiclesFragment())

//                        subMenuListVehicles =it.value.data[0].SubMenu
//                                subMenuListAssets =it.value.data[1].SubMenu
                                //sending data to repositories
                                vehiclesRepository.sendVehiclesList(listb)
                                assetRepository.sendAssetList(listc)
//                        println("Success sublist size is  ${  it.value[0]}")

                            }else{
                                print("inside search query")
                                listb.clear()


                                subMenuListVehicles.forEachIndexed { index, subMenu ->
                                    if (subMenu.device_id.toString().contains(string)) {
                                        print("matching device id")
                                        print(subMenu.device_id)
                                        listb.add(subMenu)

                                    }
                                }
                                listc = subMenuListAssets.toMutableList()
                                print("with query list size")
                                print(listb.size)
                                replaceFragment(VehiclesFragment())

//                        subMenuListVehicles =it.value.data[0].SubMenu
//                            subMenuListAssets =it.value.data[1].SubMenu
                                //sending data to repositories
                                vehiclesRepository.sendVehiclesList(listb)
                                assetRepository.sendAssetList(listc)
//                        println("Success sublist size is  ${  it.value[0]}")






                            }

                        }else if(fragment_number == 1){
                            if(string == "" ){
                                print("without query")
                                listb = subMenuListVehicles.toMutableList()
                                listc = subMenuListAssets.toMutableList()
                                print("list sixe")
                                print(listb.size)
                                replaceFragment(AssetsFragment())

//                        subMenuListVehicles =it.value.data[0].SubMenu
//                                subMenuListAssets =it.value.data[1].SubMenu
                                //sending data to repositories
                                vehiclesRepository.sendVehiclesList(listb)
                                assetRepository.sendAssetList(listc)
//                        println("Success sublist size is  ${  it.value[0]}")

                            }else{
                                print("inside search query")
                                listc.clear()


                                subMenuListAssets.forEachIndexed { index, subMenu ->
                                    if(subMenu.grp_name != null){


                                    if (subMenu.grp_name.toLowerCase().contains(string.toLowerCase())) {
                                        print("matching device id")
                                        print(subMenu.grp_name)
                                        listc.add(subMenu)

                                    }
                                    }
                                }
                                listb = subMenuListVehicles.toMutableList()
                                print("with query list size")
                                print(listc.size)
                                replaceFragment(AssetsFragment())

//                        subMenuListVehicles =it.value.data[0].SubMenu
//                            subMenuListAssets =it.value.data[1].SubMenu
                                //sending data to repositories
                                vehiclesRepository.sendVehiclesList(listb)
                                assetRepository.sendAssetList(listc)
//                        println("Success sublist size is  ${  it.value[0]}")






                            }


                        }





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
                    searchView.setQuery("", false);
                    searchView.clearFocus();

                    fragment_number = 0

                    replaceFragment(VehiclesFragment())
                }

                1 -> {
                    searchView.setQuery("", false);
                    searchView.clearFocus();

                    fragment_number = 1
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






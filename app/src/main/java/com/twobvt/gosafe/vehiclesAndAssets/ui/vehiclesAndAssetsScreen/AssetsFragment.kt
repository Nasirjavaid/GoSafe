package com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.twobvt.gosafe.base.BaseFragment
import com.twobvt.gosafe.databinding.FragmentAssetsBinding
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.AssetRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaViewModel.AssetViewModel

class AssetsFragment()  : BaseFragment<AssetViewModel,FragmentAssetsBinding,AssetRepository>(){

    private lateinit var assetAdaptor: AssetAdapter



   // private  val subMenuList : List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = subMenu

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentAssetsBinding.inflate(inflater, container, false)
//        val view = binding.root
//
//
//
//        return view
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getAssetList()
        viewModel.assetList.observe(this.viewLifecycleOwner, Observer {

            setUpViews(it)

        })




    }

    private fun setUpViews(subMenuList: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {
        binding.assetRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        assetAdaptor = AssetAdapter()
        binding.assetRecyclerView.adapter = assetAdaptor



        renderVehiclesTreeList(subMenuList)

    }


    private fun renderVehiclesTreeList(subMenu: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {
        assetAdaptor.addData(subMenu)
        assetAdaptor.notifyDataSetChanged()
    }

    override fun getViewModel()= AssetViewModel:: class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAssetsBinding.inflate(inflater,container,false)

    override fun getRepository()= AssetRepository ()
}
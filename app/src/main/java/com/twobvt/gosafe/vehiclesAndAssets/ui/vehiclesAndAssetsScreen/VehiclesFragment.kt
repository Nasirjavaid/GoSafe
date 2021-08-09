package com.twobvt.gosafe.vehiclesAndAssets.ui.vehiclesAndAssetsScreen


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.twobvt.gosafe.base.BaseFragment
import com.twobvt.gosafe.databinding.FragmentVehiclesBinding
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.VehicleRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaViewModel.VehicleViewModel


class VehiclesFragment()  : BaseFragment<VehicleViewModel, FragmentVehiclesBinding, VehicleRepository>() {



//      private  val subMenuList : List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu> = subMenu

    // This property is only valid between onCreateView and
    // onDestroyView.


    private lateinit var vehicleAdaptor: VehicleAdaptor



//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//
//        //return inflater.inflate(R.layout.fragment_vehicles, container, false)
//        _binding = FragmentVehiclesBinding.inflate(inflater, container, false)
//        val view = binding.root
//
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        registerForContextMenu(binding.parentRecyclerView);
//        setUpViews(subMenuList)
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getVehicleList()
        viewModel.vehicleList.observe(this.viewLifecycleOwner, Observer {

            setUpViews(it)

            println("List size from View model ${it.size}")

        })
    }

    private fun setUpViews(subMenuList: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {

        binding.parentRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        vehicleAdaptor = VehicleAdaptor()
        binding.parentRecyclerView.adapter = vehicleAdaptor

        renderVehiclesTreeList(subMenuList)

    }

    private fun renderVehiclesTreeList(subMenu: List<com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu>) {
        vehicleAdaptor.addData(subMenu)
        vehicleAdaptor.notifyDataSetChanged()
    }



    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)


                // Inflate Menu from xml resource
//        val menuInflater = requireActivity().menuInflater
//        menuInflater.inflate(R.menu.contexual_menu_for_vehicle_screen, menu)




        Toast.makeText(activity, " Menu Created ", Toast.LENGTH_LONG).show()
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)

        Toast.makeText(activity, " User selected something ", Toast.LENGTH_LONG).show()
    }

    override fun getViewModel()= VehicleViewModel::class.java
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentVehiclesBinding .inflate(inflater,container,false)

    override fun getRepository()= VehicleRepository ()


}
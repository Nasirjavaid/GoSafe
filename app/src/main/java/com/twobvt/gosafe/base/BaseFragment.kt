package com.twobvt.gosafe.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.network.RemoteDataSource

abstract class BaseFragment<viewModel : ViewModel, viewBinding: ViewBinding, baseRepository: BaseRepository>  : Fragment() {

    lateinit var binding : viewBinding
    lateinit var viewModel: viewModel
    val remoteDataSource = RemoteDataSource()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater,container)
        var  factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        return  binding.root
    }


    abstract fun getViewModel() : Class<viewModel>

    abstract fun getViewBinding(inflater: LayoutInflater,container: ViewGroup?) : viewBinding

    abstract fun getRepository () : baseRepository


//    // This could be moved into an abstract BaseActivity
//    // class for being re-used by several instances
//    protected open fun setFragment(fragment: Fragment) {
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.content, fragment)
//        fragmentTransaction.commit()
//    }
}
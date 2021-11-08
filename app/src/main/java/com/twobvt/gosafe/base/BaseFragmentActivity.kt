package com.twobvt.gosafe.base

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.network.RemoteDataSource

abstract class BaseFragmentActivity

<viewModel : ViewModel, viewBinding: ViewBinding, baseRepository:BaseRepository> :FragmentActivity() , View.OnClickListener  {



    lateinit var binding : viewBinding
    lateinit var viewModel: viewModel
    val remoteDataSource = RemoteDataSource()

    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        var  factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        setContentView(binding.root)

    }




//     override fun onClick(v: View?) {
//
//        when(v!!.id){
//            R.id.login_button ->{
//                println("@@@@@@@@@@@@@@@@@@@@@@@@@ new here")
//            }
//        }
//     }

    abstract fun getViewModel() : Class<viewModel>

    abstract fun getViewBinding() : viewBinding

    abstract fun getRepository () : baseRepository


    // This could be moved into an abstract BaseActivity
    // class for being re-used by several instances
    protected open fun setFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, fragment)
        fragmentTransaction.commit()
    }
}

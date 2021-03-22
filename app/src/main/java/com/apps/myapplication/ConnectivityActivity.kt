package com.apps.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.apps.myapplication.base.BaseActivity
import com.apps.myapplication.viewmodel.ConnectiveModel
import com.apps.myapplication.viewmodel.ConnectivityLiveData

class ConnectivityActivity : BaseActivity() {

    private lateinit var connectivityLiveData: ConnectivityLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connectivity2)

        connectivityLiveData = ConnectivityLiveData(application)
        connectivityLiveData.observe(this, Observer { isAvailable ->
            /*when (isAvailable) {
                true -> showToastMessage("Connected with Internet")
                false -> showToastMessage("No Network")
            }*/

            if(isAvailable)
            {
                showToastMessage("connected")
            }else{
                showToastMessage("not connected")
            }
        })
/*
        val connectiveModel = ConnectiveModel(application)
        connectiveModel.observe(this, Observer { isConnected ->
            if(isConnected)
            {
                showToastMessage("connected")
            }else{
                showToastMessage("not connected")
            }
        })*/
    }
}
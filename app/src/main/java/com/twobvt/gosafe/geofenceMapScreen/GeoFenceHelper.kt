package com.twobvt.gosafe.geofenceMapScreen

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng

import java.lang.Exception


class GeoFenceHelper(context: Context):ContextWrapper(context) {
    private final val TAG = "GEOFENCEHELPER"
    fun getGeofencingRequest(geofence: Geofence):GeofencingRequest{

    return GeofencingRequest.Builder().apply {
        addGeofence(geofence)
        setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
    }.build()

    }
    fun getGeofence( ID:String,latlng:LatLng,radius:Double,transitionType:Int) :Geofence
    {
        return Geofence.Builder().setCircularRegion(latlng.latitude,latlng.longitude, radius.toFloat())
            .setRequestId(ID)
            .setLoiteringDelay(10000)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(transitionType)
            .build()
    }
    val pendingIntent:PendingIntent by lazy {
    val intent = Intent(this,GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
    }
    fun getErrorString(e:Exception):String{
        if(e is ApiException)
        {
            val apiException = e
            when(apiException.statusCode){
                GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE-> return ("Geofence Not Available")
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> return ("Too Many Geofences to update")
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> return ("Too Many Geofences Pending")
            }
        }
        return e.localizedMessage!!

    }

}
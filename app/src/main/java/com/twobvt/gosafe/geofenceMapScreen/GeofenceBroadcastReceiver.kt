package com.twobvt.gosafe.geofenceMapScreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.content.Intent
import android.media.AudioManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    private lateinit var geofenceList : List<Geofence>
    private lateinit var audioManager : AudioManager
    override fun onReceive(context: Context?, intent: Intent?) {
        if(context!= null)
        {
            audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager

        }
        val geofenceEvent = GeofencingEvent.fromIntent(intent)
        if(geofenceEvent.hasError())
        {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofenceEvent.errorCode)
            Log.d("error",errorMessage)
            Log.d("onReceive","Geofence Event has got an error")

            val location = geofenceEvent.triggeringLocation
            val transitionTypes = geofenceEvent.geofenceTransition
            geofenceList = geofenceEvent.triggeringGeofences
            when(transitionTypes)
            {
                Geofence.GEOFENCE_TRANSITION_ENTER -> {
                    Toast.makeText(context,"You entered the Geofence",Toast.LENGTH_LONG).show()
                    print("Notifcation will fire from here")

                }
                Geofence.GEOFENCE_TRANSITION_DWELL -> {
                    Toast.makeText(context,"You Dwell the Geofence",Toast.LENGTH_LONG).show()
                    print("Notifcation will fire from here")

                }
                Geofence.GEOFENCE_TRANSITION_EXIT -> {
                    Toast.makeText(context,"You exited the Geofence",Toast.LENGTH_LONG).show()
                    print("Notifcation will fire from here")

                }
            }

        }

    }
}
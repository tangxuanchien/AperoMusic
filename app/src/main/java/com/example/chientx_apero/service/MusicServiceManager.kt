package com.example.chientx_apero.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log

object MusicServiceManager {
    private var mediaPlayerService: MusicService? = null
    private var isServiceBound = false
    private var serviceConnection: ServiceConnection? = null

    fun bindService(context: Context) {
        if (serviceConnection != null) return

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as MusicService.MusicBinder
                mediaPlayerService = binder.getService()
                isServiceBound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isServiceBound = false
                mediaPlayerService = null
            }
        }

        val intent = Intent(context, MusicService::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intent)
        } else{
            context.startService(intent)
        }
        context.bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(context: Context) {
        serviceConnection?.let {
            context.unbindService(it)
            serviceConnection = null
        }
        isServiceBound = false
        mediaPlayerService = null
    }

    fun getService(): MusicService? {
        return mediaPlayerService
    }
}
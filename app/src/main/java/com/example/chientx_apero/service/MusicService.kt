package com.example.chientx_apero.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleService
import com.example.chientx_apero.R
import java.io.File

class MusicService : LifecycleService() {
    private val TAG = "MusicService"
    private var mediaPlayer: MediaPlayer? = null


    companion object {
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_RESUME = "ACTION_RESUME"
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                val uri = Uri.fromFile(File(intent.getStringExtra("uri")!!))
                Log.d(TAG, "ACTION_PLAY received, uri = $uri")
                uri?.let {
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(applicationContext, it)
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                    Log.d(TAG, "MediaPlayer started successfully")
                    startForeground(1, createNotification())
                    Log.d(TAG, "Send Notification")
                }
            }

            ACTION_PAUSE -> {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                }
            }

            ACTION_STOP -> {
                mediaPlayer?.stop()
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "music_service",
                "Music Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, "music_service")
        notificationBuilder.setSmallIcon(R.drawable.avatar)
        notificationBuilder.setContentTitle("Music Service")
        notificationBuilder.setContentText("Music is playing")

        return notificationBuilder.build()
    }
}
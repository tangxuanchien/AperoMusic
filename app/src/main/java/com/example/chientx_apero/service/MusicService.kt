package com.example.chientx_apero.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
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
        super.onStartCommand(intent, flags, startId)
        startForeground(1, createNotification())
        var currentTime: Int = 0

        when (intent?.action) {
            ACTION_PLAY -> {
                Log.d(TAG, "onStartCommand: ${intent.getStringExtra("uri")}")
                val uri = Uri.fromFile(File(intent.getStringExtra("uri")!!))
                uri?.let {
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(applicationContext, it)
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                }
            }

            ACTION_PAUSE -> {
                if (mediaPlayer!!.isPlaying) {
                    currentTime = mediaPlayer?.currentPosition!!
                    mediaPlayer?.pause()
                }
            }

            ACTION_STOP -> {
                mediaPlayer?.stop()
                currentTime = 0
                stopSelf()
            }

            ACTION_RESUME -> {
                mediaPlayer?.start()
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun createNotification(): Notification {
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O not need because set SDK_INT is always > 29
        val channelId = "music_service"
        val channelName = "Music Service"
        val manager = getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_LOW
        )
        manager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, "music_service")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Music Service")
            .setContentText("Music is playing")
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }
}
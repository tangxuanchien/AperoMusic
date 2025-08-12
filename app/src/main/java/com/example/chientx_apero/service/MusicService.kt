package com.example.chientx_apero.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.chientx_apero.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class MusicService : LifecycleService() {
    private val TAG = "MusicService"
    private val binder = MusicBinder()

    private var mediaPlayer: MediaPlayer? = null
    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()
    private var updateJob: Job? = null

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    companion object {
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_RESUME = "ACTION_RESUME"
        const val ACTION_REPLAY = "ACTION_REPLAY"
        const val ACTION_SEEK_TO = "ACTION_SEEK_TO"
        const val ACTION_GET_POSITION = "ACTION_GET_POSITION"
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startForeground(1, createNotification())

        when (intent?.action) {
            ACTION_PLAY -> {
                val uri = Uri.fromFile(File(intent.getStringExtra("uri")!!))
                uri?.let {
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(applicationContext, it)
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                    startUpdatingPosition()
                }
            }

            ACTION_PAUSE -> {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                    stopUpdatingPosition()
                }
            }

            ACTION_STOP -> {
                mediaPlayer?.stop()
                stopUpdatingPosition()
                stopSelf()
            }

            ACTION_RESUME -> {
                mediaPlayer?.start()
                startUpdatingPosition()
            }

            ACTION_REPLAY -> {
                mediaPlayer?.isLooping = true
            }

            ACTION_SEEK_TO -> {
                val progress = intent.getFloatExtra("progress", 0f)
                mediaPlayer?.seekTo(progress.toInt())
            }
            ACTION_GET_POSITION -> {
                getCurrentPosition()
            }
        }
        return START_NOT_STICKY
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    private fun createNotification(progress: Int = 0): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "music_service"
            val channelName = "Music Service"
            val manager = getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_LOW
            )
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, "music_service")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Music Service")
            .setContentText("Progress: ${progress / 1000}s")
            .setProgress(mediaPlayer?.duration ?: 0, progress, false)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    private fun startUpdatingPosition() {
        updateJob?.cancel()
        updateJob = lifecycleScope.launch {
            while (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                _currentPosition.emit(mediaPlayer?.currentPosition ?: 0)
                delay(1000)
            }
        }
    }

    private fun stopUpdatingPosition() {
        updateJob?.cancel()
        updateJob = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopUpdatingPosition()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
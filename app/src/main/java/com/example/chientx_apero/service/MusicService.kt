package com.example.chientx_apero.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.chientx_apero.R
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.ui.player.PlayerIntent
import java.io.File

class MusicService : LifecycleService() {
    private val TAG = "MusicService"
    private val binder = MusicBinder()

    private var mediaPlayer: MediaPlayer? = null

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
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREVIOUS = "ACTION_PREVIOUS"
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        when (intent?.action) {
            ACTION_PLAY -> {
                val uri = Uri.fromFile(File(intent.getStringExtra("uri")!!))
                uri?.let {
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(applicationContext, it)
                    mediaPlayer?.prepareAsync()
                    mediaPlayer?.setOnPreparedListener {
                        mediaPlayer?.start()
                        startForeground(1, createNotification())
                    }
                }
            }

            ACTION_PAUSE -> {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                    updateNotification()
                }
                AppCache.isPlayingSong.value = false
            }

            ACTION_STOP -> {
                mediaPlayer?.stop()
                stopForeground(true)
                AppCache.playingSong = null
                stopSelf()
            }

            ACTION_RESUME -> {
                mediaPlayer?.start()
                updateNotification()
                AppCache.isPlayingSong.value = true
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

            ACTION_NEXT -> {
                MusicManager.processIntent(PlayerIntent.NextSong, this)
            }

            ACTION_PREVIOUS -> {
                MusicManager.processIntent(PlayerIntent.PreviousSong, this)
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
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, "music_service")
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setSmallIcon(R.drawable.logo_app)
            .setLargeIcon(AppCache.playingSong?.image)
            .setContentTitle(AppCache.playingSong?.name)
            .setContentText(AppCache.playingSong?.artist)
            .addAction(R.drawable.seek_to_back, "", getPendingIntent(ACTION_PREVIOUS))
            .addAction(
                if (mediaPlayer?.isPlaying == true) {
                    R.drawable.pause_fill
                } else {
                    R.drawable.play_fill
                },
                "",
                getPendingIntent(
                    if (mediaPlayer?.isPlaying == true) {
                        ACTION_PAUSE
                    } else {
                        ACTION_RESUME
                    }
                )
            )
            .addAction(R.drawable.seek_to_next, "", getPendingIntent(ACTION_NEXT))
            .addAction(R.drawable.tick, "", getPendingIntent(ACTION_STOP))
            .setDeleteIntent(getPendingIntent(ACTION_STOP))
            .build()
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(
            this, action.hashCode(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun updateNotification() {
        val notification = createNotification()
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
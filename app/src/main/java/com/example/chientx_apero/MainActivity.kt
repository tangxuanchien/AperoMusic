package com.example.chientx_apero

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.navigation.Navigation
import com.example.chientx_apero.room_db.repository.SongRepository
import com.example.chientx_apero.ui.playlist.components.getAllMp3Files
import com.example.chientx_apero.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
            == PackageManager.PERMISSION_GRANTED
        ) {
            loadMp3AndSaveDb()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
                1
            )
        }
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Navigation()
            }
        }
    }

    private fun loadMp3AndSaveDb() {
        CoroutineScope(Dispatchers.IO).launch {
            val allSongs = getAllMp3Files(this@MainActivity)
            AppCache.allSongs.clear()
            AppCache.allSongs.addAll(allSongs)

            val repository = SongRepository(this@MainActivity)
            repository.insertSongs(allSongs)
        }
    }
}
package com.example.chientx_apero

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chientx_apero.model.PreferenceManager
import com.example.chientx_apero.navigation.Navigation
import com.example.chientx_apero.room_db.repository.SongRepository
import com.example.chientx_apero.ui.playlist.components.getAllMp3Files
import com.example.chientx_apero.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        PreferenceManager.init(applicationContext)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                loadMp3AndSaveDb()
            } else {
                Log.d("Permission", "Permission denied")
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
            == PackageManager.PERMISSION_GRANTED
        ) {
            loadMp3AndSaveDb()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
        }

//        Bug not send notification in first time allow permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
        }

        setContent {
            AppTheme {
                Navigation()
            }
        }
    }

    private fun loadMp3AndSaveDb() {
        CoroutineScope(Dispatchers.IO).launch {
            val allSongs = getAllMp3Files(this@MainActivity)
            Log.d("Main", "${allSongs.size}")
            val repository = SongRepository(this@MainActivity)
            repository.insertSongs(allSongs)
            Log.d("Main", "Insert success")
        }
    }
}
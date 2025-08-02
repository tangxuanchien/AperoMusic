package com.example.chientx_apero.library_screen

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.chientx_apero.model.Song
import java.io.File


fun shareDataToDevice(context: Context, song: Song) {
    val file = File(song.data)
    val uri = FileProvider.getUriForFile(
        context,
        context.packageName + ".provider",
        file
    )

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "audio/*"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share music via"))
}
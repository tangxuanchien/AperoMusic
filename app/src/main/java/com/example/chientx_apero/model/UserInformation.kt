package com.example.chientx_apero.model

import android.R.id.content
import android.net.Uri
import com.example.chientx_apero.R
import androidx.core.net.toUri

object UserInformation {
    var name: String = ""
    var phone: String = ""
    var university: String = ""
    var describe: String = ""
    var imageUri: Uri =
        "content://media/picker/0/com.android.providers.media.photopicker/media/43".toUri()
}
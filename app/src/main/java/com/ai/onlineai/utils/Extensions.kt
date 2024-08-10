package com.ai.onlineai.utils

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ai.onlineai.MainActivity
import java.util.concurrent.Executors

fun MainActivity.requestCameraPermission(){
    when{
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED -> {
            this.shouldShowCamera.value = true
        }

        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.CAMERA
        ) -> Log.i("PERMISSION", "Show camera permission dialog")

        else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}

fun MainActivity.startCamera(){
    cameraExecutor = Executors.newSingleThreadExecutor()
}

fun MainActivity.stopCamera(){
    cameraExecutor.shutdown()
}
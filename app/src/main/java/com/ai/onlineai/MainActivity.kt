package com.ai.onlineai

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ai.onlineai.ui.screens.Camera
import com.ai.onlineai.ui.theme.MyApplicationTheme
import com.ai.onlineai.utils.requestCameraPermission
import com.ai.onlineai.utils.startCamera
import com.ai.onlineai.utils.stopCamera
import java.util.concurrent.ExecutorService

class MainActivity : ComponentActivity() {
    var shouldShowCamera: MutableState<Boolean> = mutableStateOf(true)
    lateinit var cameraExecutor: ExecutorService

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        shouldShowCamera.value = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestCameraPermission()
        startCamera()
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (shouldShowCamera.value){
                        Camera(executor = cameraExecutor, onError = { Log.e("Camera Error", "Error") } )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestCameraPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopCamera()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {

    }
}
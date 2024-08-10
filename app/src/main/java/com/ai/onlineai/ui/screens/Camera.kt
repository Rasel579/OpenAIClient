package com.ai.onlineai.ui.screens

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun Camera(
    executor: Executor, onError: (ImageCaptureException) -> Unit
) {
    val context  = LocalContext.current
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val lifeCicleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }

    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY).build()
    }

    val cameraSelector: CameraSelector = CameraSelector.Builder()
                                                       .requireLensFacing(lensFacing)
                                                       .build()
    LaunchedEffect(lensFacing){
        val cameraProvider: ProcessCameraProvider = suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).also { cameraProvider ->
                run {
                    cameraProvider.addListener({
                        continuation.resume(cameraProvider.get())
                    }, ContextCompat.getMainExecutor(context))
                }
            }
        }

        cameraProvider.unbindAll()
        val camera = cameraProvider.bindToLifecycle(
            lifecycleOwner = lifeCicleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.surfaceProvider = previewView.surfaceProvider
        previewView.scaleType = PreviewView.ScaleType.FILL_CENTER

    }
    
    AndroidView(factory = { previewView }, Modifier.fillMaxSize())

}
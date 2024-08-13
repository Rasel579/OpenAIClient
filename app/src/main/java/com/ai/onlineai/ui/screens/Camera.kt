package com.ai.onlineai.ui.screens

import android.graphics.Bitmap
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
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
import com.ai.onlineai.models.NetModelResponse
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Camera(
    executor: Executor,
    viewModel: NetViewModel,
    onError: (ImageCaptureException) -> Unit
) {
    val context = LocalContext.current
    var stringBitmap = ""
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val lifeCicleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
    imageAnalysis.setAnalyzer(executor, ImageAnalysis.Analyzer { imageProxy ->
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageProxy.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        stringBitmap = Base64.encodeToString(byteArray, Base64.URL_SAFE)
        viewModel.detectObject(NetModelResponse(name = "test", imageByteArray = stringBitmap))
        imageProxy.close()
    })

    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY).build()
    }

    val videoCapture = VideoCapture.withOutput(Recorder.Builder().build())


    val cameraSelector: CameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(lensFacing) {
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
            imageCapture,
            videoCapture,
            imageAnalysis
        )

        preview.surfaceProvider = previewView.surfaceProvider
        previewView.scaleType = PreviewView.ScaleType.FILL_CENTER

        com.ai.onlineai.utils.MediaCodec.config()
    }

    AndroidView(factory = { previewView }, Modifier.fillMaxSize())

}
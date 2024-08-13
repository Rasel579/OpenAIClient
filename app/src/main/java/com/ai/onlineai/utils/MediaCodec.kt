package com.ai.onlineai.utils

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.util.Log
import android.view.Surface

object MediaCodec {
    private val mediaCodec = MediaCodec.createEncoderByType("video/avc")
    private val width = 320
    private val height = 240
    private val colorFormat = MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
    private val videoBitrate = 500000
    private val videoFrameBySec = 20
    private val iFrameInterval = 3
    lateinit var encoder: Surface

    val format = MediaFormat.createVideoFormat("video/avc", width, height).also {
        it.setInteger(MediaFormat.KEY_COLOR_FORMAT, colorFormat)
        it.setInteger(MediaFormat.KEY_BIT_RATE, videoBitrate)
        it.setInteger(MediaFormat.KEY_FRAME_RATE, videoFrameBySec)
        it.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, iFrameInterval)
    }

    fun config() {
        mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        encoder = mediaCodec.createInputSurface()
        mediaCodec.setCallback(EncoderCallback())
        mediaCodec.start()
        Log.i("Codec", "Start")
    }

}
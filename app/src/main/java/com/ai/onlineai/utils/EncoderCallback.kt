package com.ai.onlineai.utils

import android.media.MediaCodec
import android.media.MediaCodec.Callback
import android.media.MediaFormat
import android.util.Log

class EncoderCallback : Callback() {
    override fun onInputBufferAvailable(p0: MediaCodec, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun onOutputBufferAvailable(
        codec: MediaCodec,
        index: Int,
        info: MediaCodec.BufferInfo
    ) {
        val buffer = codec.getOutputBuffer(index)
        Log.i("DataBuffer", buffer?.asCharBuffer().toString())
    }

    override fun onError(p0: MediaCodec, p1: MediaCodec.CodecException) {
        TODO("Not yet implemented")
    }

    override fun onOutputFormatChanged(p0: MediaCodec, p1: MediaFormat) {
        TODO("Not yet implemented")
    }

}
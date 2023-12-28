package com.example.testnav

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale


import java.util.*

class TextToSpeechManager(context: Context) {
    private val textToSpeech = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS) {
            // Xử lý khi TextToSpeech được khởi tạo thành công (nếu cần)
        }
    }

    fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun shutdown() {
        textToSpeech.shutdown()
    }
}
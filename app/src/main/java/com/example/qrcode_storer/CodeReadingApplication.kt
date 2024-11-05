package com.example.qrcode_storer

import android.app.Application
import com.example.qrcode_storer.model.CodeContainer

class CodeReadingApplication : Application() {
    lateinit var container: CodeContainer

    override fun onCreate() {
        super.onCreate()
        container = CodeContainer(this)
    }
}
package com.example.qrcode_storer.model

import android.content.Context

class CodeContainer(private val context: Context) {
    val codeRepository: CodeRepository by lazy {
        CodeRepository(AppDatabase.getAppDatabase(context).codeDao())
    }
}
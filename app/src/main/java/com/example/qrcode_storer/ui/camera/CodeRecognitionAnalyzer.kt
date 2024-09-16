package com.example.qrcode_storer.ui.camera

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CodeRecognitionAnalyzer(
    private val onDetectedTextUpdated: (String) -> Unit
) : ImageAnalysis.Analyzer {

    companion object {
        const val THROTTLE_TIMEOUT_MS = 1_000L
    }

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val codeScanner: BarcodeScanner = BarcodeScanning.getClient()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        scope.launch {
            val mediaImage: Image = imageProxy.image ?: run { imageProxy.close(); return@launch }
            val inputImage: InputImage =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            suspendCoroutine { continuation ->
                codeScanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->
                        var detectedText: String = ""

                        for (barcode in barcodes) {
                            detectedText += barcode.rawValue + "\n"
                        }
                        onDetectedTextUpdated(detectedText)

                    }
                    .addOnCompleteListener {
                        continuation.resume(Unit)
                    }
            }

            delay(THROTTLE_TIMEOUT_MS)
        }.invokeOnCompletion { exception ->
            exception?.printStackTrace()
            imageProxy.close()
        }
    }

}
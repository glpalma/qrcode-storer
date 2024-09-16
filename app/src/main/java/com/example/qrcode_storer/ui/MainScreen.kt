
package com.example.qrcode_storer.ui

import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.runtime.Composable
import com.example.qrcode_storer.ui.camera.CameraScreen
import com.example.qrcode_storer.ui.no_permission.NoPermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {

    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    MainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )

}

@Composable
private fun MainContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {

    if (hasPermission) {
        CameraScreen()
    } else {
        NoPermissionScreen(onRequestPermission)
    }

}

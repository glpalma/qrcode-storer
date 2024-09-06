@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.qrcode_storer.ui

import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.runtime.Composable

@Composable
fun MainScreen() {

    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    MainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )

}
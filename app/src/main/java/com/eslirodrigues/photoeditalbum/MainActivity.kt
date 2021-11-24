package com.eslirodrigues.photoeditalbum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eslirodrigues.photoeditalbum.presentation.PhotoCaptureScreen
import com.eslirodrigues.photoeditalbum.presentation.PhotoListScreen
import com.eslirodrigues.photoeditalbum.presentation.ScreenNav
import com.eslirodrigues.photoeditalbum.ui.theme.PhotoEditAlbumTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoEditAlbumTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenNav.PhotoList.route
                ) {
                    composable(route = ScreenNav.PhotoList.route) {
                        PhotoListScreen(navController = navController)
                    }
                    composable(route = ScreenNav.PhotoCapture.route) {
                        PhotoCaptureScreen(navController = navController)
                    }
                }
            }
        }
    }
}
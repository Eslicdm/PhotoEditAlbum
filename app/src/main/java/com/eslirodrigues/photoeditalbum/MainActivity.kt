package com.eslirodrigues.photoeditalbum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eslirodrigues.photoeditalbum.presentation.*
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
                    composable(
                        route = ScreenNav.PhotoScreen.route + "?photoUri={photoUri}",
                        arguments = listOf(
                            navArgument(
                                name = "photoUri"
                            ) {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val photoUri = it.arguments?.getString("photoUri") ?: ""
                        PhotoScreen(
                            photoUri = photoUri
                        )
                    }
                }
            }
        }
    }
}
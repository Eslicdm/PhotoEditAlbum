package com.eslirodrigues.photoeditalbum

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eslirodrigues.photoeditalbum.presentation.*
import com.eslirodrigues.photoeditalbum.ui.theme.DarkGray
import com.eslirodrigues.photoeditalbum.ui.theme.PhotoEditAlbumTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

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
                    startDestination = ScreenNav.SplashScreen.route
                ) {
                    composable(route = ScreenNav.PhotoList.route) {
                        PhotoListScreen(navController = navController)
                    }
                    composable(route = ScreenNav.PhotoCapture.route) {
                        PhotoCaptureScreen(navController = navController)
                    }
                    composable(route = ScreenNav.SplashScreen.route) {
                        SplashScreen(navController = navController)
                    }
                    composable(
                        route = ScreenNav.PhotoScreen.route + "?photoUri={photoUri}&photoName={photoName}",
                        arguments = listOf(
                            navArgument(
                                name = "photoUri"
                            ) {
                                type = NavType.StringType
                            },
                            navArgument(
                                name = "photoName"
                            ) {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val photoUri = it.arguments?.getString("photoUri") ?: ""
                        val photoName = it.arguments?.getString("photoName") ?: ""
                        PhotoScreen(
                            navController = navController,
                            photoUri = photoUri,
                            photoName = photoName
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(1500L)
        navController.navigate(ScreenNav.PhotoList.route) {
            popUpTo(ScreenNav.SplashScreen.route) {
                inclusive = true
            }
        }
    }
    Column(
        modifier = Modifier
            .background(DarkGray)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.scale(scale.value).width(200.dp),
            painter = painterResource(id = R.drawable.ic_photoeditalbum),
            contentDescription = stringResource(id = R.string.app_name)
        )
    }
}
package com.eslirodrigues.photoeditalbum.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eslirodrigues.photoeditalbum.MainActivity
import com.eslirodrigues.photoeditalbum.SplashScreen
import com.eslirodrigues.photoeditalbum.core.util.Constants.BTN_TAKE_PHOTO
import com.eslirodrigues.photoeditalbum.core.util.Constants.FAB_CAMERA
import com.eslirodrigues.photoeditalbum.core.util.Constants.PHOTO_ITEM
import com.eslirodrigues.photoeditalbum.di.PhotoModule
import com.eslirodrigues.photoeditalbum.ui.theme.PhotoEditAlbumTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(PhotoModule::class)
class PhotoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalPermissionsApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
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

    @Test
    fun savePhoto_isDisplayed() {
        composeRule.onNodeWithTag(FAB_CAMERA).performClick()
        runBlocking {
            composeRule.onNodeWithTag(BTN_TAKE_PHOTO).performClick()
            delay(2000)
        }
        composeRule.onAllNodesWithTag(PHOTO_ITEM)[0].assertIsDisplayed()
    }
}
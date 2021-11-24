package com.eslirodrigues.photoeditalbum.presentation

sealed class ScreenNav(val route: String) {
    object PhotoList: ScreenNav(route = "photo_list_screen")
    object PhotoCapture: ScreenNav(route = "photo_capture_screen")
}
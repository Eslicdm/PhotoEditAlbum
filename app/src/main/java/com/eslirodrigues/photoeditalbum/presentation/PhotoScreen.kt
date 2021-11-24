package com.eslirodrigues.photoeditalbum.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eslirodrigues.photoeditalbum.ui.theme.LightGray
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun PhotoScreen(
    photoUri: String,
) {
    Column(
        modifier = Modifier
            .background(LightGray)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = rememberGlidePainter(Uri.parse(photoUri)),
            contentDescription = null
        )
    }
}
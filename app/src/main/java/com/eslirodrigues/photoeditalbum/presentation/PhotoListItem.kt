package com.eslirodrigues.photoeditalbum.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.eslirodrigues.photoeditalbum.data.model.Photo
import com.eslirodrigues.photoeditalbum.ui.theme.LightGray
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun PhotoListItem(navController: NavController, photo: Photo) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(route = ScreenNav.PhotoScreen.route + "?photoUri=${photo.uri}&photoName=${photo.name}")
            },
        backgroundColor = LightGray,
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(14.dp))
    ) {
        Column(modifier = Modifier
            .padding(10.dp)) {
            Image(
                painter = rememberGlidePainter(photo.uri.toUri()),
                contentDescription = null
            )
        }
    }
}
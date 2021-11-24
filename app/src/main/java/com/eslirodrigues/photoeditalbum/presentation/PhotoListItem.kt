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
import androidx.hilt.navigation.compose.hiltViewModel
import com.eslirodrigues.photoeditalbum.data.model.Photo
import com.eslirodrigues.photoeditalbum.presentation.viewmodel.PhotoViewModel
import com.eslirodrigues.photoeditalbum.ui.theme.LightGray
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun PhotoListItem(photo: Photo, viewModel: PhotoViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable {
                viewModel.deletePhoto(photo)
            },
        backgroundColor = LightGray,
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(14.dp))
    ) {
        Column(modifier = Modifier
            .padding(10.dp)) {
            Image(
                painter = rememberGlidePainter(Uri.parse(photo.uri)),
                contentDescription = null
            )
        }
    }
}
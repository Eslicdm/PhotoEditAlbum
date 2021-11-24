package com.eslirodrigues.photoeditalbum.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eslirodrigues.photoeditalbum.R
import com.eslirodrigues.photoeditalbum.presentation.viewmodel.PhotoViewModel

@ExperimentalFoundationApi
@Composable
fun PhotoListScreen(
    viewModel: PhotoViewModel = hiltViewModel(),
    navController: NavController
) {
    val photoList = viewModel.readAllPhotos.observeAsState(listOf()).value

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(ScreenNav.PhotoCapture.route)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_photo),
                    contentDescription = stringResource(id = R.string.add_photo),
                )
            }
        }
    ) {
        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
            itemsIndexed(photoList) { index, photoItem ->
                PhotoListItem(photoItem)
            }
        }
    }
}

@Preview
@Composable
fun PreviewPhotoListScreen() {

}
package com.eslirodrigues.photoeditalbum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslirodrigues.photoeditalbum.data.model.Photo
import com.eslirodrigues.photoeditalbum.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
) : ViewModel() {

    val readAllPhotos = photoRepository.getPhoto()

    fun savePhoto(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            photoRepository.savePhoto(photo)
        }
    }

    fun deletePhoto(photoUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            photoRepository.deletePhoto(photoUri)
        }
    }
}
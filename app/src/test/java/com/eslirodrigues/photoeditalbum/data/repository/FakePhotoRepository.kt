package com.eslirodrigues.photoeditalbum.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eslirodrigues.photoeditalbum.data.model.Photo

class FakePhotoRepository : PhotoRepository {

    private val photos = mutableListOf<Photo>()

    private val observablePhotos = MutableLiveData(photos)

    private fun refreshLiveData() {
        observablePhotos.postValue(photos)
    }

    override suspend fun savePhoto(photo: Photo) {
        photos.add(photo)
        refreshLiveData()
    }

    override fun getPhoto(): LiveData<MutableList<Photo>> {
        return observablePhotos
    }

    override suspend fun deletePhoto(photoUri: String) {
        photos.removeIf { photo ->
            photo.uri == photoUri
        }
        refreshLiveData()
    }
}
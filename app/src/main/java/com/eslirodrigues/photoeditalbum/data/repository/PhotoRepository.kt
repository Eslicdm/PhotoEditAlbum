package com.eslirodrigues.photoeditalbum.data.repository

import androidx.lifecycle.LiveData
import com.eslirodrigues.photoeditalbum.data.model.Photo

interface PhotoRepository {

    fun getPhoto() : LiveData<MutableList<Photo>>
    suspend fun savePhoto(photo: Photo)
    suspend fun deletePhoto(photoUri: String)
}
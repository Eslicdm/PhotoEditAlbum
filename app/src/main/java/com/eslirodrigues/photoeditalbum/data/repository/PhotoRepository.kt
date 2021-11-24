package com.eslirodrigues.photoeditalbum.data.repository

import androidx.core.net.toUri
import com.eslirodrigues.photoeditalbum.data.database.PhotoDao
import com.eslirodrigues.photoeditalbum.data.model.Photo
import java.io.File
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photoDao: PhotoDao,
) {

    val readAllPhotos = photoDao.getPhoto()

    suspend fun savePhoto(photo: Photo) {
        return photoDao.savePhoto(photo)
    }

    suspend fun deletePhoto(photoUri: String) {
        File(photoUri.toUri().path).delete()
        return photoDao.deletePhoto(photoUri)
    }
}
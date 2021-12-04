package com.eslirodrigues.photoeditalbum.data.repository

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import com.eslirodrigues.photoeditalbum.data.database.PhotoDao
import com.eslirodrigues.photoeditalbum.data.model.Photo
import java.io.File
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao,
) : PhotoRepository {

    override fun getPhoto(): LiveData<MutableList<Photo>> {
        return photoDao.getPhoto()
    }

    override suspend fun savePhoto(photo: Photo) {
        return photoDao.savePhoto(photo)
    }

    override suspend fun deletePhoto(photoUri: String) {
        File(photoUri.toUri().path).delete()
        return photoDao.deletePhoto(photoUri)
    }
}
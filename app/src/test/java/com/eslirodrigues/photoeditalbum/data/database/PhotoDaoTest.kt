package com.eslirodrigues.photoeditalbum.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eslirodrigues.photoeditalbum.data.model.Photo
import com.eslirodrigues.photoeditalbum.data.repository.FakePhotoRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PhotoDaoTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var fakePhotoRepository: FakePhotoRepository

    @Before
    fun setup() {
        fakePhotoRepository = FakePhotoRepository()
    }

    private fun getRandomPhotos() {
        val photosInsert = mutableListOf<Photo>()
        ('a'..'z').forEachIndexed { index, c ->
            photosInsert.add(
                Photo(
                    name = c.toString(),
                    uri = c.toString()
                )
            )
        }
        photosInsert.shuffle()
        runBlocking {
            photosInsert.forEach { fakePhotoRepository.savePhoto(it) }
        }
    }

    @Test
    fun getPhoto_NotEmpty() = runBlocking {
        getRandomPhotos()
        val photoList = fakePhotoRepository.getPhoto()

        assertThat(photoList).isNotNull()
    }

    @Test
    fun getPhoto_CorrectSize() = runBlocking {
        getRandomPhotos()
        val photoList = fakePhotoRepository.getPhoto()

        assertThat(photoList.value!!.size).isEqualTo(26)
    }

    @Test
    fun savePhoto_isDeleted() = runBlocking {
        val photo = Photo(name = "car", uri = "file/dev")
        fakePhotoRepository.savePhoto(photo)

        val photoList = fakePhotoRepository.getPhoto()

        fakePhotoRepository.deletePhoto(photo.uri)

        assertThat(photoList.value).isEmpty()
    }
}
package com.eslirodrigues.photoeditalbum.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eslirodrigues.photoeditalbum.data.model.Photo

@Dao
interface PhotoDao {

    @Insert
    suspend fun savePhoto(photo: Photo)

    @Query("SELECT * FROM photo_table")
    fun getPhoto() : LiveData<MutableList<Photo>>

    @Query("DELETE FROM photo_table WHERE uri = :photoUri")
    suspend fun deletePhoto(photoUri: String)
}
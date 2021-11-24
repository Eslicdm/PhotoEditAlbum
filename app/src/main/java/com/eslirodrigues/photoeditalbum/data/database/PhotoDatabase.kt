package com.eslirodrigues.photoeditalbum.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eslirodrigues.photoeditalbum.data.model.Photo


@Database(entities = [Photo::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao() : PhotoDao
}
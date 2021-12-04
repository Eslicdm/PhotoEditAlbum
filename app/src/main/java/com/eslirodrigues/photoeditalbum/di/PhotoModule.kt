package com.eslirodrigues.photoeditalbum.di

import android.content.Context
import androidx.room.Room
import com.eslirodrigues.photoeditalbum.data.database.PhotoDatabase
import com.eslirodrigues.photoeditalbum.data.repository.PhotoRepositoryImpl
import com.eslirodrigues.photoeditalbum.data.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {

    @Singleton
    @Provides
    fun providePhotoDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PhotoDatabase::class.java,
        "photo_db"
    ).build()

    @Singleton
    @Provides
    fun providePhotoDao(db: PhotoDatabase) = db.photoDao()

    @Singleton
    @Provides
    fun provideRepository(db: PhotoDatabase): PhotoRepository = PhotoRepositoryImpl(db.photoDao())
}
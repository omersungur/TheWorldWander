package com.omersungur.theworldwander.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omersungur.theworldwander.data.local.database.entity.ImageToDelete
import com.omersungur.theworldwander.data.local.database.entity.ImageToUpload

@Database(
    entities = [ImageToUpload::class, ImageToDelete::class],
    version = 2,
    exportSchema = false
)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imageToUploadDao(): ImageToUploadDao
    abstract fun imageToDeleteDao(): ImageToDeleteDao
}

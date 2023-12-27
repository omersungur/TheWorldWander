package com.omersungur.theworldwander.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omersungur.theworldwander.core.Constants.IMAGE_TO_UPLOAD_TABLE

@Entity(tableName = IMAGE_TO_UPLOAD_TABLE)
data class ImageToUpload(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteImagePath: String,
    val imageUrl: String,
    val sessionUri: String
)

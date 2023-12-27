package com.omersungur.theworldwander.domain.model

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf

class GalleryState {
    val images = mutableStateListOf<GalleryImage>()
    val imagesToBeDeleted = mutableStateListOf<GalleryImage>()

    fun addImage(galleryImage: GalleryImage) {
        images.add(galleryImage)
    }

    fun removeImages(galleryImage: GalleryImage) {
        imagesToBeDeleted.add(galleryImage)
        images.remove(galleryImage)
    }
}

data class GalleryImage(
    val image: Uri,
    val remoteImagePath: String = ""
)
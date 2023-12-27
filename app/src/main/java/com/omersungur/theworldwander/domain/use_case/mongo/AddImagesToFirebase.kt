package com.omersungur.theworldwander.domain.use_case.mongo

import com.google.firebase.storage.FirebaseStorage
import com.omersungur.theworldwander.data.local.database.ImageToUploadDao
import com.omersungur.theworldwander.data.local.database.entity.ImageToUpload
import com.omersungur.theworldwander.domain.model.GalleryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddImagesToFirebase @Inject constructor(
    private val imageToUploadDao: ImageToUploadDao
) {
    operator fun invoke(
        galleryState: GalleryState,
        scope: CoroutineScope
    ) {
        val storage = FirebaseStorage.getInstance().reference
        galleryState.images.forEach { galleryImage ->
            val imagePath = storage.child(galleryImage.remoteImagePath)
            imagePath.putFile(galleryImage.image)
                .addOnProgressListener {
                    val sessionUri = it.uploadSessionUri
                    if (sessionUri != null) {
                        scope.launch(Dispatchers.IO) {
                            imageToUploadDao.addImageToUpload(
                                ImageToUpload(
                                    remoteImagePath = galleryImage.remoteImagePath,
                                    imageUrl = galleryImage.image.toString(),
                                    sessionUri = sessionUri.toString()
                                )
                            )
                        }
                    }
                }
        }
    }
}
package com.omersungur.theworldwander.domain.use_case.mongo

import com.google.firebase.storage.FirebaseStorage
import com.omersungur.theworldwander.data.local.database.ImageToDeleteDao
import com.omersungur.theworldwander.data.local.database.entity.ImageToDelete
import com.omersungur.theworldwander.domain.model.GalleryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteImagesFromFirebase @Inject constructor(
    private val imageToDeleteDao: ImageToDeleteDao
) {
    operator fun invoke(images: List<String>?, scope: CoroutineScope, galleryState: GalleryState) {
        val storage = FirebaseStorage.getInstance().reference
        if (images != null) {
            images.forEach { remotePath ->
                storage.child(remotePath).delete()
                    .addOnFailureListener {
                        scope.launch(Dispatchers.IO) {
                            imageToDeleteDao.addImageToDelete(
                                ImageToDelete(remoteImagePath = remotePath)
                            )
                        }
                    }
            }
        } else {
            galleryState.imagesToBeDeleted.map { it.remoteImagePath }.forEach { remotePath ->
                storage.child(remotePath).delete()
                    .addOnFailureListener {
                        scope.launch(Dispatchers.IO) {
                            imageToDeleteDao.addImageToDelete(
                                ImageToDelete(remoteImagePath = remotePath)
                            )
                        }
                    }
            }
        }
    }
}
package com.omersungur.theworldwander.domain.use_case.mongo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.data.local.database.ImageToDeleteDao
import com.omersungur.theworldwander.data.local.database.entity.ImageToDelete
import com.omersungur.theworldwander.data.repository.CountryMongoRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAllImagesFromMongoDB @Inject constructor(
    val imageToDeleteDao: ImageToDeleteDao
) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        scope: CoroutineScope
    ) {

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val imagesDirectory = "images/$userId"
        val storage = FirebaseStorage.getInstance().reference
        storage.child(imagesDirectory)
            .listAll()
            .addOnSuccessListener {
                it.items.forEach { ref ->
                    val imagePath = "images/$userId/${ref.name}"
                    storage.child(imagePath).delete()
                        .addOnFailureListener {
                            scope.launch(Dispatchers.IO) {
                                imageToDeleteDao.addImageToDelete(
                                    ImageToDelete(
                                        remoteImagePath = imagePath
                                    )
                                )
                            }
                        }
                }
                scope.launch(Dispatchers.IO) {
                    val result = CountryMongoRepositoryImpl.deleteAllImagesInMongoDB()
                    if (result is Resource.Success) {
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    } else if (result is Resource.Error) {
                        withContext(Dispatchers.Main) {
                            onError(result.message.toString())
                        }
                    }
                }
            }.addOnFailureListener { onError(it.toString()) }
    }
}
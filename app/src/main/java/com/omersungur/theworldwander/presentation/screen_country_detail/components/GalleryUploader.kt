package com.omersungur.theworldwander.presentation.screen_country_detail.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.theworldwander.domain.model.GalleryImage
import com.omersungur.theworldwander.domain.model.GalleryState
import com.omersungur.theworldwander.presentation.screen_country_list.components.LastImageOverlay
import com.omersungur.theworldwander.presentation.ui.theme.Elevation

@Composable
fun GalleryUploader(
    modifier: Modifier = Modifier,
    galleryState: GalleryState,
    imageSize: Dp = 40.dp,
    imageShape: CornerBasedShape = Shapes().medium,
    spaceBetweenImages: Dp = 12.dp,
    onAddClicked: () -> Unit,
    onImageSelect: (Uri) -> Unit,
    onImageClicked: (GalleryImage) -> Unit
) {
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { images ->
        images.forEach {
            onImageSelect(it)
        }
    }

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.BottomStart) {
        val numberOfVisibleImages = remember {
            derivedStateOf {
                this.maxWidth.div(spaceBetweenImages + imageSize).toInt().minus(2)
            }
        }

        val remainingImages = remember {
            derivedStateOf {
                galleryState.images.size - numberOfVisibleImages.value
            }
        }

        Row {
            AddImageButton(
                imageSize = imageSize,
                imageShape = imageShape
            ) {
                onAddClicked()
                multiplePhotoPicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
            Spacer(modifier = Modifier.width(spaceBetweenImages))
            galleryState.images.take(numberOfVisibleImages.value).forEach { galleryImage ->
                AsyncImage(
                    modifier = Modifier
                        .clip(imageShape)
                        .size(imageSize)
                        .clickable { onImageClicked(galleryImage) },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(galleryImage.image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Gallery Image"
                )
                Spacer(modifier = Modifier.width(spaceBetweenImages))
            }

            if (remainingImages.value > 0) {
                LastImageOverlay(
                    imageSize = imageSize,
                    imageShape = imageShape,
                    remainingImages = remainingImages.value
                )
            }
        }
    }
}

@Composable
fun AddImageButton(
    imageSize: Dp,
    imageShape: CornerBasedShape,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(imageSize)
            .clip(imageShape),
        onClick = onClick,
        tonalElevation = Elevation.Level1
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Icon"
            )
        }
    }
}

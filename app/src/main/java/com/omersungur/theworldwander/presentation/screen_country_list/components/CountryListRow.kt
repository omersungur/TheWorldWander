package com.omersungur.theworldwander.presentation.screen_country_list.components

import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.theworldwander.core.fetchImagesFromFirebase
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.presentation.screen_country_list.CountryListViewModel
import com.omersungur.theworldwander.presentation.ui.theme.Elevation

@Composable
fun CountryRow(
    country: CountryMongo,
    onItemClicked: (String) -> Unit,
    imageShape: CornerBasedShape = Shapes().small,
    imageSizeWidth: Dp = 130.dp,
    imageSizeHeight: Dp = 75.dp,
    viewModel: CountryListViewModel
) {
    val context = LocalContext.current
    var galleryOpened by remember { mutableStateOf(false) }
    val downloadedImages = remember { mutableStateListOf<Uri>() }
    var galleryLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = galleryOpened) {
        if (galleryOpened && downloadedImages.isEmpty()) {
            galleryLoading = true
            fetchImagesFromFirebase(
                remoteImagePaths = country.images,
                onImageDownload = { image ->
                    downloadedImages.add(image)
                },
                onImageDownloadFailed = {
                    Toast.makeText(
                        context,
                        "Images not uploaded yet." +
                                "Wait a little bit, or try uploading again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    galleryLoading = false
                    galleryOpened = false
                },
                onReadyToDisplay = {
                    galleryLoading = false
                    galleryOpened = true
                }
            )
        }
    }

    Box(
        modifier = Modifier.clickable(onClick = {
            viewModel.saveLastClickedCountry(country.countryName)
            onItemClicked(country._id.toHexString())
        })
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = Elevation.Level3
            ), modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = country.countryName,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 18.sp, fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    AsyncImage(
                        modifier = Modifier
                            .clip(imageShape)
                            .size(width = imageSizeWidth, height = imageSizeHeight),
                        model = ImageRequest.Builder(LocalContext.current).data(country.countryFlag)
                            .crossfade(true).build(),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Gallery Image"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        CustomCountriesInfoText(text = "Region: ${country.countryRegion}")
                        CustomCountriesInfoText(text = "Language: ${country.countryLanguage}")
                        CustomCountriesInfoText(text = "Currency: ${country.countryCurrency}")
                        CustomCountriesInfoText(text = "Population: ${country.countryPopulation}")
                    }
                }

                if (country.images.isNotEmpty()) {
                    ShowGalleryButton(
                        galleryOpened = galleryOpened,
                        galleryLoading = galleryLoading
                    ) {
                        galleryOpened = !galleryOpened
                    }
                }

                AnimatedVisibility(
                    visible = galleryOpened && !galleryLoading,
                    enter = fadeIn() + expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Gallery(images = downloadedImages)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomCountriesInfoText(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        text = text,
        fontSize = 14.sp,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Light,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ShowGalleryButton(
    galleryOpened: Boolean,
    galleryLoading: Boolean,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text(
            text = if (galleryOpened) {
                if (galleryLoading) "Loading" else "Hide Gallery"
            } else {
                "Show Gallery"
            },
            style = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize)
        )
    }
}

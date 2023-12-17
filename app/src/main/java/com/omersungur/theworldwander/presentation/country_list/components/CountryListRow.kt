package com.omersungur.theworldwander.presentation.country_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.theworldwander.domain.model.Country
import com.omersungur.theworldwander.presentation.ui.theme.Elevation

@Composable
fun CountryRow(
    country: Country,
    imageShape: CornerBasedShape = Shapes().small,
    imageSizeWidth: Dp = 130.dp,
    imageSizeHeight: Dp = 75.dp,
) {
    Box {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = Elevation.Level3
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(imageShape)
                        .size(width = imageSizeWidth, height = imageSizeHeight)
                        .clickable { },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(country.flag)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Gallery Image"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.width(imageSizeWidth),
                    text = country.name ?: "No Data Found!",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

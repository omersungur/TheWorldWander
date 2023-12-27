package com.omersungur.theworldwander.presentation.screen_country_detail.components

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.Details
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.domain.model.GalleryImage
import com.omersungur.theworldwander.domain.model.GalleryState
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryDetailContent(
    paddingValues: PaddingValues,
    country: CountryMongo,
    imageShape: CornerBasedShape = Shapes().small,
    imageSizeWidth: Dp = 1000.dp,
    imageSizeHeight: Dp = 200.dp,
    galleryState: GalleryState,
    onSaveClicked: (CountryMongo) -> Unit,
    onImageSelect: (Uri) -> Unit,
    onImageClicked: (GalleryImage) -> Unit
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }

    val tabItems = listOf(
        TabItem(
            title = "Detail",
            unselectedIcon = Icons.Outlined.Details,
            selectedIcon = Icons.Filled.Details
        ),
        TabItem(
            title = "Map",
            unselectedIcon = Icons.Outlined.Map,
            selectedIcon = Icons.Filled.Map
        )
    )

    val selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { tabItems.size })

//    LaunchedEffect(key1 = selectedTabIndex) {
//        pagerState.animateScrollToPage(selectedTabIndex)
//    }
//    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
//        if (!pagerState.isScrollInProgress) {
//            selectedTabIndex = pagerState.currentPage
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = tabItem.title)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) tabItem.selectedIcon else tabItem.unselectedIcon,
                            contentDescription = tabItem.title
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState
        ) { index ->
            if (tabItems[index].title == "Detail") {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .clip(imageShape)
                            .size(width = imageSizeWidth, height = imageSizeHeight),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(country.countryFlag)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Gallery Image"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CountryInfoText(text = "Name: ${country.countryName}")
                    CountryInfoText(text = "Region: ${country.countryRegion}")
                    CountryInfoText(text = "Capital: ${country.countryCapital}")
                    CountryInfoText(text = "Language: ${country.countryLanguage}")
                    CountryInfoText(text = "Currency: ${country.countryCurrency}")
                    CountryInfoText(text = "Population: ${country.countryPopulation}")
                    if (country.isCountryIndependent) {
                        CountryInfoText(text = "Independent: Yes")
                    } else {
                        CountryInfoText(text = "Independent: No")
                    }
                    CountryInfoText(text = "Code: ${country.countryCode}")
                    CountryInfoText(text = "Time Zone: ${country.countryTimeZones}")
                    CountryInfoText(text = "Calling Code: ${country.countryCallingCode}")
                    CountryInfoText(text = "Borders: ${country.countryBorders}")

                    Spacer(modifier = Modifier.height(12.dp))

                    GalleryUploader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        galleryState = galleryState,
                        onAddClicked = { focusManager.clearFocus() },
                        onImageSelect = onImageSelect,
                        onImageClicked = onImageClicked
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        onClick = {
                            onSaveClicked(
                                CountryMongo().apply {
                                    this.images =
                                        galleryState.images.map { it.remoteImagePath }.toRealmList()
                                })
                        },
                        shape = Shapes().small
                    ) {
                        Text(text = "Save")
                    }
                }
            }
            if (tabItems[index].title == "Map") {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val countryLatLng = LatLng(
                        country.countryLatitude.toDouble(),
                        country.countryLongitude.toDouble()
                    )
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(countryLatLng, 10f)
                    }
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = properties,
                        uiSettings = uiSettings
                    ) {
                        Marker(
                            state = MarkerState(position = countryLatLng),
                            title = country.countryName,
                            snippet = "Marker in Singapore"
                        )
                    }
                    Switch(
                        checked = uiSettings.zoomControlsEnabled,
                        onCheckedChange = {
                            uiSettings = uiSettings.copy(zoomControlsEnabled = it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CountryInfoText(text: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        fontSize = 18.sp,
        textAlign = TextAlign.Center
    )
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)
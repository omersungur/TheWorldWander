package com.omersungur.theworldwander.presentation.screen_country_list

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.omersungur.theworldwander.core.Constants.APP_ID
import com.omersungur.theworldwander.presentation.Screen
import com.omersungur.theworldwander.presentation.base.DisplayAlertDialog
import com.omersungur.theworldwander.presentation.screen_country_list.components.CountryListContent
import com.omersungur.theworldwander.presentation.screen_country_list.components.CountryListTopBar
import com.omersungur.theworldwander.presentation.screen_country_list.components.CustomProgressBar
import com.omersungur.theworldwander.presentation.screen_country_list.components.NavigationDrawer
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    countryListViewModel: CountryListViewModel = hiltViewModel(),
    navController: NavHostController,
    navigateToCountryDetail: (String) -> Unit
) {
    val state = countryListViewModel.countryListState
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var deleteAllDialogOpened by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var openDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }

    NavigationDrawer(
        drawerState = drawerState,
        onDeleteAllClicked = { deleteAllDialogOpened = true }
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Filter") },
                    icon = { Icon(Icons.Filled.FilterList, contentDescription = "") },
                    onClick = {
                        showBottomSheet.value = true
                    }
                )
            },
            topBar = {
                CountryListTopBar(
                    scrollBehavior = scrollBehavior,
                    onLogOutClick = { openDialog = true },
                    onMenuClicked = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },

            content = { paddingValues ->
                Box {
                    CountryListContent(
                        paddingValues = paddingValues,
                        countries = state.countryMongoList,
                        onItemClicked = navigateToCountryDetail,
                        viewModel = countryListViewModel
                    )
                    if (state.isLoading) {
                        CustomProgressBar(
                            modifier = Modifier
                                .zIndex(1f)
                                .fillMaxSize()
                        )
                    }
                }

                if (showBottomSheet.value) {
                    ModalBottomSheet(
                        modifier = Modifier
                            .padding(8.dp)
                            .navigationBarsPadding(),
                        onDismissRequest = {
                            showBottomSheet.value = false
                        },
                        sheetState = sheetState
                    ) {
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "All"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Asia"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Africa"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Europe"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Americas"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Oceania"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Polar"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Antarctic"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Antarctic Ocean"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Population (Descending)"
                        )
                        BottomSheetButton(
                            scope = scope,
                            countryListViewModel = countryListViewModel,
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            query = "Population (Ascending)"
                        )
                    }
                }

                DisplayAlertDialog(
                    title = "Sign Out",
                    message = "Do you want to sign out?",
                    dialogOpened = openDialog,
                    onDialogClosed = { openDialog = false },
                    onYesClicked = {
                        val user = App.create(APP_ID).currentUser
                        user?.let {
                            scope.launch(Dispatchers.IO) {
                                user.logOut()
                                withContext(Dispatchers.Main) {
                                    navController.navigate(Screen.AuthScreen.route)
                                }
                            }
                        }
                    })
                DisplayAlertDialog(
                    title = "Delete All Images",
                    message = "Do you want to delete all images?",
                    dialogOpened = deleteAllDialogOpened,
                    onDialogClosed = { deleteAllDialogOpened = false },
                    onYesClicked = {
                        countryListViewModel.deleteAllImagesInMongoDB(
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "All Images Deleted.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            onError = {
                                Toast.makeText(
                                    context,
                                    if (it == "No Internet Connection.") {
                                        "We need an Internet Connection for this operation!"
                                    } else {
                                        it
                                    },
                                    Toast.LENGTH_SHORT
                                ).show()
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    })
            })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetButton(
    scope: CoroutineScope,
    countryListViewModel: CountryListViewModel,
    sheetState: SheetState,
    query: String,
    showBottomSheet: MutableState<Boolean>
) {

    Button(
        modifier = Modifier
            .padding(start = 8.dp, bottom = 2.dp)
            .navigationBarsPadding(),
        onClick = {
            scope.launch {
                countryListViewModel.filterCountries(query)
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet.value = false
                }
            }
        }) {
        Text(query)
    }
}

//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(state.countryList.size) { i ->
//            val country = state.countryList[i]
//            CountryRow(country = country)
//        }
//    }

//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(items = state.countryList) { country ->
//            CountryRow(country = country)
//            Spacer(modifier = Modifier.height(25.dp))
//        }
//    }
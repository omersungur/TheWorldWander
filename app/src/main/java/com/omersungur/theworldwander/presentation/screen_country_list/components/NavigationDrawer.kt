package com.omersungur.theworldwander.presentation.screen_country_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.omersungur.theworldwander.R

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    onDeleteAllClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.height(250.dp),
                        painter = painterResource(id = R.drawable.world),
                        contentDescription = "Google Logo"
                    )
                }
                NavigationDrawerItem(
                    label = {
                        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                            Image(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon"
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = "Delete All Diaries")
                        }
                    },
                    selected = false,
                    onClick = onDeleteAllClicked
                )
            }
        },
        content = content
    )
}

package com.omersungur.theworldwander

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.omersungur.theworldwander.ui.theme.TheWorldWanderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheWorldWanderTheme {

            }
        }
    }
}
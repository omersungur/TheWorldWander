package com.omersungur.theworldwander.presentation.screen_auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.omersungur.theworldwander.core.Constants.CLIENT_ID
import com.omersungur.theworldwander.presentation.screen_auth.components.AuthenticationContent
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    navigateToHome: () -> Unit
) {
    val viewModel: AuthenticationViewModel = hiltViewModel()
    val authenticated by viewModel.authenticated
    val loadingState by viewModel.loadingState
    val oneTapState = rememberOneTapSignInState()
    val messageBarState = rememberMessageBarState()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding(),
        content = {
            ContentWithMessageBar(messageBarState = messageBarState) {
                AuthenticationContent(
                    loadingState = loadingState,
                    onButtonClicked = {
                        oneTapState.open()
                        viewModel.setLoadingState(true)
                    }
                )
            }
        }
    )

    OneTapSignInWithGoogle(
        state = oneTapState,
        clientId = CLIENT_ID,
        onTokenIdReceived = { tokenId ->
            val credential = GoogleAuthProvider.getCredential(tokenId, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModel.signInWithMongoAtlas(
                            tokenId = tokenId,
                            onSuccess = {
                                messageBarState.addSuccess("Successfully Authenticated!")
                                viewModel.setLoadingState(false)
                            },
                            onError = { error ->
                                messageBarState.addError(error)
                                viewModel.setLoadingState(false)
                            }
                        )
                    } else {
                        task.exception?.let { exception ->
                            messageBarState.addError(exception)
                            viewModel.setLoadingState(false)
                        }
                    }
                }
            viewModel.signInWithMongoAtlas(
                tokenId = tokenId,
                onSuccess = {
                    messageBarState.addSuccess("Successfully Authenticated!")
                    viewModel.setLoadingState(false)
                },
                onError = { error ->
                    messageBarState.addError(error)
                    viewModel.setLoadingState(false)
                }
            )
        },
        onDialogDismissed = { message ->
            messageBarState.addError(Exception(message))
            viewModel.setLoadingState(false)
        }
    )

    LaunchedEffect(key1 = authenticated) {
        if (authenticated) {
            navigateToHome()
        }
    }
}
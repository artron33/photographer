package com.pierre.photo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pierre.photo.ui.theme.PhotoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhotoTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                PhotoApp(
                    photoUIState = uiState,
                    controlFavorite = viewModel::addFavorite,
                    openDetailScreen = viewModel::openDetailScreen,
                    searchPhotoWith = viewModel::searchPhotoBy,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoAppPreview() {
    PhotoTheme {
        PhotoApp(
            photoUIState = PhotoUIState(),
        )
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 1100)
@Composable
fun PhotoAppPreviewDesktopPortrait() {
    PhotoTheme {
        PhotoApp(
            photoUIState = PhotoUIState(),
        )
    }
}

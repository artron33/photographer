package com.pierre.photo.presentation.screen.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.pierre.photo.presentation.screen.PhotoApp
import com.pierre.photo.presentation.screen.PhotoViewModel
import com.pierre.photo.presentation.screen.PhotographerUi
import com.pierre.photo.presentation.theme.PhotoTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    private val viewModel: PhotoViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhotoTheme {
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)
                val uiState = viewModel.uiState
                val uiDetailState = viewModel.uiDetailState
                val photographerPagingItems: MutableStateFlow<PagingData<PhotographerUi>> = viewModel.photographerState
                val favoritesPagingItems: MutableStateFlow<PagingData<PhotographerUi>> = viewModel.favoritesState
                Log.e("yallah", "mainActivity.setContent")

                PhotoApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    photoUIState = uiState,
                    uiDetailState = uiDetailState,
                    photographerPagingItems = photographerPagingItems,
                    favoritesPagingItems = favoritesPagingItems,
                    openDetailScreen = viewModel::openDetailScreen,
                    searchPhotoWith = viewModel::searchPhotographerBy,
                    controlFavorite = viewModel::addFavorite,
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun PhotoAppPreview() {
    PhotoTheme {
        PhotoApp(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
            displayFeatures = emptyList(),

//            photoUIState = PhotoUIState(
//                favorites = LocalPhotographersDataProvider.photographers ,
//                favoriteDetail = LocalPhotographersDataProvider.photographerDetail
//            ),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 700, heightDp = 500)
@Composable
fun PhotoAppPreviewTablet() {
    PhotoTheme {
        PhotoApp(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(700.dp, 500.dp)),
            displayFeatures = emptyList(),

//            photoUIState = PhotoUIState(
//                favorites = LocalPhotographersDataProvider.photographers ,
//                favoriteDetail = LocalPhotographersDataProvider.photographerDetail
//            ),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 500, heightDp = 700)
@Composable
fun PhotoAppPreviewTabletPortrait() {
    PhotoTheme {
        PhotoApp(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(500.dp, 700.dp)),
            displayFeatures = emptyList(),

//            photoUIState = PhotoUIState(
//                favorites = LocalPhotographersDataProvider.photographers ,
//                favoriteDetail = LocalPhotographersDataProvider.photographerDetail
//            ),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1100, heightDp = 600)
@Composable
fun PhotoAppPreviewDesktop() {
    PhotoTheme {
        PhotoApp(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(1100.dp, 600.dp)),
            displayFeatures = emptyList(),

//            photoUIState = PhotoUIState(
//                favorites = LocalPhotographersDataProvider.photographers ,
//                favoriteDetail = LocalPhotographersDataProvider.photographerDetail
//            ),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 600, heightDp = 1100)
@Composable
fun PhotoAppPreviewDesktopPortrait() {
    PhotoTheme {
        PhotoApp(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(600.dp, 1100.dp)),
            displayFeatures = emptyList(),

//            photoUIState = PhotoUIState(
//                favorites = LocalPhotographersDataProvider.photographers ,
//                favoriteDetail = LocalPhotographersDataProvider.photographerDetail
//            ),
        )
    }
}

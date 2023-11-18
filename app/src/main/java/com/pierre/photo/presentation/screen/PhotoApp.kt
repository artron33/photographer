package com.pierre.photo.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.pierre.photo.data.localmock.LocalPhotographersDataProvider
import com.pierre.photo.presentation.navigation.ModalNavigationDrawerContent
import com.pierre.photo.presentation.navigation.PermanentNavigationDrawerContent
import com.pierre.photo.presentation.navigation.ReplyBottomNavigationBar
import com.pierre.photo.presentation.navigation.ReplyNavigationActions
import com.pierre.photo.presentation.navigation.ReplyNavigationRail
import com.pierre.photo.presentation.navigation.ReplyRoute
import com.pierre.photo.presentation.navigation.ReplyTopLevelDestination
import com.pierre.photo.presentation.utils.DevicePosture
import com.pierre.photo.presentation.utils.ReplyContentType
import com.pierre.photo.presentation.utils.ReplyNavigationContentPosition
import com.pierre.photo.presentation.utils.ReplyNavigationType
import com.pierre.photo.presentation.utils.isBookPosture
import com.pierre.photo.presentation.utils.isSeparating
import com.pierre.photo.presentation.screen.comingsoon.EmptyComingSoon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    photoUIState: StateFlow<PhotoUIState>? = null,
    closeDetailScreen: () -> Unit = {},
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit = { _, _ -> },
    searchPhotoWith: (String) -> Unit = { _ -> },
    controlFavorite: (PhotographerUi) -> Unit = { },
    photographerPagingItems: MutableStateFlow<PagingData<PhotographerUi>>? = null,
    favoritesPagingItems: MutableStateFlow<PagingData<PhotographerUi>>? = null,
    uiDetailState: StateFlow<isDetailState>?=null,
) {

    /**
     * This will help us select type of navigation and content type depending on window size and
     * fold state of the device.
     */
    val navigationType: ReplyNavigationType
    val contentType: ReplyContentType

    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.SINGLE_PANE
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ReplyContentType.DUAL_PANE
            } else {
                ReplyContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                ReplyNavigationType.NAVIGATION_RAIL
            } else {
                ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ReplyContentType.DUAL_PANE
        }

        else -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.SINGLE_PANE
        }
    }

    /**
     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
     * ergonomics and reachability depending upon the height of the device.
     */
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            ReplyNavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            ReplyNavigationContentPosition.CENTER
        }

        else -> {
            ReplyNavigationContentPosition.TOP
        }
    }

    PhotoNavigationWrapper(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition,
        photoUIState = photoUIState,
        uiDetailState = uiDetailState,
        closeDetailScreen = closeDetailScreen,
        searchPhotoWith = searchPhotoWith,
        controlFavorite = controlFavorite,
        openDetailScreen = openDetailScreen,
        photographerPagingItems = photographerPagingItems,
        favoritesPagingItems = favoritesPagingItems,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhotoNavigationWrapper(
    photoUIState: StateFlow<PhotoUIState>?,

    searchPhotoWith: (String) -> Unit,
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit,

    displayFeatures: List<DisplayFeature>,
    navigationType: ReplyNavigationType,
    contentType: ReplyContentType,
    navigationContentPosition: ReplyNavigationContentPosition,
    closeDetailScreen: () -> Unit,
    photographerPagingItems: MutableStateFlow<PagingData<PhotographerUi>>?,
    favoritesPagingItems: MutableStateFlow<PagingData<PhotographerUi>>?,
    uiDetailState: StateFlow<isDetailState>?,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        ReplyNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: ReplyRoute.FAVORITES

    if (navigationType == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        // TODO check on custom width of PermanentNavigationDrawer: b/232495216
        PermanentNavigationDrawer(drawerContent = {
            PermanentNavigationDrawerContent(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigationActions::navigateTo,
            )
        }) {
            PhotoAppContent(
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                photoUIState = photoUIState,
                uiDetailState = uiDetailState,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                closeDetailScreen = closeDetailScreen,
                searchPhotoWith = searchPhotoWith,
                controlFavorite = controlFavorite,
                openDetailScreen = openDetailScreen,
                photographerPagingItems = photographerPagingItems,
                favoritesPagingItems = favoritesPagingItems,
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                    onDrawerClicked = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            },
            drawerState = drawerState
        ) {
            PhotoAppContent(
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                photoUIState = photoUIState,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                closeDetailScreen = closeDetailScreen,
//                navigateToDetail = navigateToDetail,
//                toggleSelectedEmail = toggleSelectedEmail

                searchPhotoWith = searchPhotoWith,
                controlFavorite = controlFavorite,
                openDetailScreen = openDetailScreen,
                onDrawerClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                photographerPagingItems = photographerPagingItems,
                favoritesPagingItems = favoritesPagingItems,
                uiDetailState = uiDetailState,
            )
        }
    }
}


@Composable
fun PhotoAppContent(
    modifier: Modifier = Modifier,
    navigationType: ReplyNavigationType,
    contentType: ReplyContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: ReplyNavigationContentPosition,
    photoUIState: StateFlow<PhotoUIState>?,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (ReplyTopLevelDestination) -> Unit,
    closeDetailScreen: () -> Unit,
    searchPhotoWith: (String) -> Unit,
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit,
    onDrawerClicked: () -> Unit = {},
    photographerPagingItems: MutableStateFlow<PagingData<PhotographerUi>>? = null,
    favoritesPagingItems: MutableStateFlow<PagingData<PhotographerUi>>?,
    uiDetailState: StateFlow<isDetailState>?,
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == ReplyNavigationType.NAVIGATION_RAIL) {
            ReplyNavigationRail(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = onDrawerClicked,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            ReplyNavHost(
                navController = navController,
                contentType = contentType,
                displayFeatures = displayFeatures,
                photoUIState = photoUIState,
                uiDetailState = uiDetailState,
                closeDetailScreen = closeDetailScreen,
                modifier = Modifier.weight(1f),
                searchPhotoWith = searchPhotoWith,
                controlFavorite = controlFavorite,
                openDetailScreen = openDetailScreen,
                photographerPagingItems = photographerPagingItems,
                favoritesPagingItems = favoritesPagingItems,
            )
            AnimatedVisibility(visible = navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
                ReplyBottomNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}

@Composable
private fun ReplyNavHost(
    navController: NavHostController,
    contentType: ReplyContentType,
    displayFeatures: List<DisplayFeature>,
    photoUIState: StateFlow<PhotoUIState>?,
    closeDetailScreen: () -> Unit,
    modifier: Modifier = Modifier,
    searchPhotoWith: (String) -> Unit,
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit,
    photographerPagingItems: MutableStateFlow<PagingData<PhotographerUi>>?,
    favoritesPagingItems: MutableStateFlow<PagingData<PhotographerUi>>?,
    uiDetailState: StateFlow<isDetailState>?
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ReplyRoute.FAVORITES,
    ) {
        composable(ReplyRoute.FAVORITES) {
            ReplyInboxScreen(
                route = ReplyRoute.FAVORITES,
                contentType = contentType,
                photoUIState = photoUIState,
                uiDetailState = uiDetailState,
                displayFeatures = displayFeatures,
                closeDetailScreen = closeDetailScreen,
                searchPhotoWith = searchPhotoWith,
                controlFavorite = controlFavorite,
                openDetailScreen = openDetailScreen,
                photographerPagingItems = photographerPagingItems,
                favoritesPagingItems = favoritesPagingItems,
            )
        }
        composable(ReplyRoute.SEARCH) {
            ReplyInboxScreen(
                contentType = contentType,
                photoUIState = photoUIState,
                displayFeatures = displayFeatures,
                closeDetailScreen = closeDetailScreen,
                route = ReplyRoute.SEARCH,
                searchPhotoWith = searchPhotoWith,
                controlFavorite = controlFavorite,
                openDetailScreen = openDetailScreen,
                photographerPagingItems = photographerPagingItems,
                favoritesPagingItems = favoritesPagingItems,
                uiDetailState = uiDetailState,
            )
        }
        composable(ReplyRoute.COMING_SOON) {
            EmptyComingSoon()
        }
    }
}


@Preview
@Composable
fun PhotoAppContentPreview() {
    PhotoNavigationWrapper(
//        route = ReplyRoute.SEARCH,
        photoUIState = null,
        controlFavorite = {},
        openDetailScreen = { _, _ -> },
        contentType = ReplyContentType.SINGLE_PANE,
        uiDetailState = null,
        displayFeatures = emptyList(),
        closeDetailScreen = {},
        searchPhotoWith = {},
        photographerPagingItems = null,
        favoritesPagingItems = null,
        navigationType = ReplyNavigationType.BOTTOM_NAVIGATION,
        navigationContentPosition = ReplyNavigationContentPosition.CENTER,
//        photoUiStatePlaceholder = PhotoUIState(
//            searchDetail = LocalPhotographersDataProvider.photographerDetail,
//            favoriteDetail = LocalPhotographersDataProvider.photographerDetail
//        ),
//        isDetailPlaceholder= isDetailState(
//            isSearchOpen = false,
//            isFavoriteOpen = false
//        ),
//        listDataPlaceholder = LocalPhotographersDataProvider.photographers,
//        emailLazyListState = rememberLazyListState()
    )

}
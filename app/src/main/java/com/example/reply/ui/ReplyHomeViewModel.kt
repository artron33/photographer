/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.reply.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.reply.data.favoris.FavoriteRepository
import com.example.reply.data.favoris.FavoriteRepositoryImpl
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer
import com.example.reply.data.photographer.PhotographersRepository
import com.example.reply.data.photographer.PhotographersRepositoryImpl
import com.example.reply.data.photographer.Src
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReplyHomeViewModel(
    private val photographerRepository: PhotographersRepository = PhotographersRepositoryImpl(),
    private val favoriteRepository: FavoriteRepository = FavoriteRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReplyHomeUIState(loading = true))
    val uiState: StateFlow<ReplyHomeUIState> = _uiState

    init {
        observeFavorite()
    }

    private fun observeEmails(searchedString: String) {
        if (searchedString.isNullOrBlank()) {
            observeFavorite();
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val data = photographerRepository.getPhotographersByName(searchedString)
            _uiState.value = ReplyHomeUIState(
                photographers = data.photos,
            )
        }
    }

    private fun observeFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = favoriteRepository.getAllFavorites()
            data.first().let {
                Log.d("TAG", "observeFavorite      : $it")
                Log.d("TAG", "observeFavorite.SIZE : ${it.size}")
                _uiState.value = ReplyHomeUIState(
                    favorites = it.toMutableList(),
//                    photographers = it.map {
//                        Log.d("TAG", "observeFavorite.SIZE : ${it.photographer}")
//                        Log.d("TAG", "observeFavorite.SIZE : ${it.photographer}")
//                        Photographer(
//                            id = it.id,
//                            width =     it.width,
//                            height =     it.height,
//                            url =     it.url,
//                            photographer =     it.photographer,
//                            photographer_url =     it.photographer_url,
//                            photographer_id =     it.photographer_id,
//                            avg_color =     it.avg_color,
//                            src = Src(
//                                0,
//                                    original = it.original,
//                                    medium =     it.medium,
//                                    small =     it.small,
//                                    landscape =     it.landscape,
//                            )
//                        )
//                    }.toList(),
//                    favorites = it.toMutableList(),
                )
            }
        }
    }

    fun addFavorite(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
             favoriteRepository.addThisFavorites(favorite)
            _uiState.value.favorites.add(favorite)
        }
    }


    ////////////////////
    ////////////////////
    ////////////////////
    fun setOpenedEmail(photographer: String) {
        observeEmails(photographer)
    }
    fun openDetailScreen(photographer: Photographer?) {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = true,
                bigScreen = photographer,
            )
    }
    ////////////////////
    ////////////////////
    ////////////////////
}

data class ReplyHomeUIState(
    val photographers: List<Photographer> = emptyList(),
    val favorites: MutableList<Favorite> = mutableListOf(),
    val bigScreen: Photographer? = null,
    val selectedEmails: Set<Long> = emptySet(),
    val openedEmail: Photographer? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)

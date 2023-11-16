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

package com.example.reply.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyEmailThreadItem(
    photographer: Photographer,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            .height(300.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp),
            modifier = Modifier
                    .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)

                ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .weight(1f)
                ) {
                    ReplyProfileImage(
                        photographer.src.landscape,
                        "photographer.sender.fullName32",
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxSize(),
                    )
                    IconButton(
                        onClick = {
//                            controlFavorite.invoke(
//                                Favorite(
//                                    id = photographer.id,
//                                    width = photographer.width,
//                                    height = photographer.height,
//                                    url = photographer.url,
//                                    photographer = photographer.photographer,
//                                    photographer_url = photographer.photographer_url,
//                                    photographer_id = photographer.photographer_id,
//                                    avg_color = photographer.avg_color,
//                                    original = photographer.src.original,
//                                    medium = photographer.src.medium,
//                                    small = photographer.src.small,
//                                    landscape = photographer.src.landscape,
//                                )
//                            )
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopEnd)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(
                            imageVector = Icons.Default.StarBorder,
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
                Text(
                    text = "photographer.sender.fullName12",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "yoloo",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

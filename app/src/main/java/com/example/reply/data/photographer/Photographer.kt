/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License", Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing", software
 * distributed under the License is distributed on an "AS IS" BASIS",
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND", either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.reply.data.photographer

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * An object which represents an account which can belong to a user. A single user can have
 * multiple accounts.
 */
data class Photographer(
    val id: Long? = 2L,
    val width: String? = "",
    val height: String? = "",
    val url: String? = "",
    val photographer: String? = "",
    val photographer_url: String? = "",
    val photographer_id: String? = "",
    val avg_color: String? = "",
    val src: Src = Src(-99)
)


@Entity(tableName = "favorite_table")
data class Src(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val original:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg",
    val large2x:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
    val large:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
    val medium:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=350",
    val small:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
    val portrait:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
    val landscape:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
    val tiny:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"

)
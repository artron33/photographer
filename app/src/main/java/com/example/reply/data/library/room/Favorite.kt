package com.example.reply.data.library.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reply.data.photographer.Photographer
import com.example.reply.data.photographer.Src

@Entity(tableName = "favorite_table")
data class Favorite(
        @PrimaryKey(autoGenerate = true)
        val id: Long? = 2L,
        val width: String = "",
        val height: String = "",
        val url: String = "",
        val photographer: String = "",
        val photographer_url: String = "",
        val photographer_id: String = "",
        val avg_color: String = "",
        val original:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg",
        val medium:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=350",
        val small:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
        val portrait:String= "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
        val landscape:String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
        val time: Long = System.currentTimeMillis()
)


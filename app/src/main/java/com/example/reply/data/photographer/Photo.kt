package com.example.reply.data.photographer

import androidx.annotation.DrawableRes

/**
 * An object which represents an account which can belong to a user. A single user can have
 * multiple accounts.
 */
data class Photo(
    val photos: List<Photographer> = mutableListOf()
)
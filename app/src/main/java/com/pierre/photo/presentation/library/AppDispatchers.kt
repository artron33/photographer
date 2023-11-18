package com.pierre.photo.presentation.library

class AppDispatchers {
    companion object {
        val IO = kotlinx.coroutines.Dispatchers.IO
        val MAIN = kotlinx.coroutines.Dispatchers.Main
        val DEFAULT = kotlinx.coroutines.Dispatchers.Default
        val UNCONFINED = kotlinx.coroutines.Dispatchers.Unconfined
    }
}
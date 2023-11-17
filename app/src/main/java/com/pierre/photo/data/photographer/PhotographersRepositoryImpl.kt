package com.pierre.photo.data.photographer

import com.pierre.photo.data.library.retrofit.RetrofitApp

class PhotographersRepositoryImpl(
    val service: PhotographerService = RetrofitApp.photographerService
) : PhotographersRepository {
    override suspend fun getPhotographersByName(name: String) = service.listRepos(name)

}

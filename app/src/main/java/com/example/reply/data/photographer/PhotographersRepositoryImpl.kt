package com.example.reply.data.photographer

import com.example.reply.data.library.retrofit.RetrofitApp

class PhotographersRepositoryImpl(
    val service: PhotographerService = RetrofitApp.photographerService
) : PhotographersRepository {
    override suspend fun getPhotographersByName(name: String) = service.listRepos(name)

}

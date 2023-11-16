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

package com.example.reply.data.photographer

import android.util.Log
import com.example.reply.data.library.retrofit.RetrofitApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PhotographersRepositoryImpl(
    val service: PhotographerService = RetrofitApp.photographerService
) : PhotographersRepository {
    override suspend fun getAllPhotographers() = suspendCoroutine<Photo> { continuation ->
        Log.e("yallah", "start 1")

        service.listRepos("ben")?.enqueue(object :Callback<Photo>{
            override fun onResponse(
                call: Call<Photo>,
                response: Response<Photo>
            ) {

                Log.e("yallah", "onResponse GG  1"+response.body())
                    response?.let {
                        Log.e("yallah", "onResponse GG  1")
                        continuation.resume(it.body()!!)
                    }
            }

            override fun onFailure(call: retrofit2.Call<Photo>, t: Throwable) {
                Log.e("yallah", "error 1"+t.stackTrace)
                Log.e("yallah", "onResponse ERROR  1 :: "+t.message)
            }

        })
    }

    override suspend fun getPhotographersByName(name: String) = suspendCoroutine { continuation ->
        Log.e("yallah", "start 44")

        service.listRepos(name)?.enqueue(object :Callback<Photo>{
            override fun onResponse(
                call: Call<Photo>,
                response: Response<Photo>
            ) {

                Log.e("yallah", "onResponse GG  44"+response.body())
                response?.let {
                    Log.e("yallah", "onResponse GG  44")
                    continuation.resume(it.body()!!)
                }
            }

            override fun onFailure(call: retrofit2.Call<Photo>, t: Throwable) {
                Log.e("yallah", "error 44"+t.stackTrace)
                Log.e("yallah", "onResponse ERROR  44 :: "+t.message)
            }

        })
    }

}

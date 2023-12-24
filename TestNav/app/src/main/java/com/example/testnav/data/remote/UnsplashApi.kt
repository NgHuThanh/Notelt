package com.example.testnav.data.remote

import com.example.testnav.model.UnsplashImage
import com.google.android.datatransport.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {
    //@Headers("Authorization: Client-ID ${BuildConfig.API}")
    @GET("/photos")
    suspend fun getAllImages(
        @Query(value="page")page:Int,
        @Query("per_page")per_page:Int,
    ):List<UnsplashImage>

}
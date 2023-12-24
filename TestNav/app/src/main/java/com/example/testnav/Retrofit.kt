package com.example.testnav

import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {
    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("client_id") clientId: String,
        @Query("per_page") perPage: Int = 1
    ): UnsplashResponse
}

data class UnsplashResponse(val results: List<UnsplashPhoto>)

data class UnsplashPhoto(val urls: UnsplashPhotoUrls, val user: UnsplashUser)

data class UnsplashPhotoUrls(val regular: String)

data class UnsplashUser(val name: String)
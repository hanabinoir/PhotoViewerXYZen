package xyz.hanabinoir.photoviewerxyzen.network

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.hanabinoir.photoviewerxyzen.data.PhotoSearch

interface PhotoService {

    @GET("search")
    suspend fun getPhotos(@Query("query") query: String?): PhotoSearch
}
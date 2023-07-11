package xyz.hanabinoir.photoviewerxyzen.network

import kotlinx.coroutines.flow.Flow
import xyz.hanabinoir.photoviewerxyzen.data.PhotoSearch

interface PhotoRepository {
    suspend fun getPhotos(query: String, page: Int?): Flow<PhotoSearch>
}
package xyz.hanabinoir.photoviewerxyzen.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.hanabinoir.photoviewerxyzen.data.PhotoSearch

class PhotoRepositoryImpl(private val service: PhotoService): PhotoRepository {

    override suspend fun getPhotos(query: String): Flow<PhotoSearch> = flow {
        emit(service.getPhotos(query))
    }
}
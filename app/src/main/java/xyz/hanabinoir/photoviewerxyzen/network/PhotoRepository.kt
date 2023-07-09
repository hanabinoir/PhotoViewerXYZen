package xyz.hanabinoir.photoviewerxyzen.network

class PhotoRepository {

    private val service = APIService.photoService

    suspend fun getPhotos(query: String) = service.getPhotos(query)
}
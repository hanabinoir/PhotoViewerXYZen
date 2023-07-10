package xyz.hanabinoir.photoviewerxyzen.network

class PhotoRepository {

    private val service = APIService.photoService

    suspend fun getPhotos(query: String? = null) = service.getPhotos(query)
}
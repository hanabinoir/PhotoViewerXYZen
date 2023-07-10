package xyz.hanabinoir.photoviewerxyzen.data

data class PhotoSearch(
    val photos: List<Photo>
)

data class Photo(
    val id: Int,
    val photographer: String,
    val alt: String,
    val src: PhotoSrcModel
)

data class PhotoSrcModel(
    val original: String,
    val tiny: String
)

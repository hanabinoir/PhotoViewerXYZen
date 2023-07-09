package xyz.hanabinoir.photoviewerxyzen.data

data class PhotoListModel(
    val photos: List<PhotoModel>
)

data class PhotoModel(
    val id: Int,
    val photographer: String,
    val alt: String,
    val src: PhotoSrcModel
)

data class PhotoSrcModel(
    val original: String,
    val tiny: String
)

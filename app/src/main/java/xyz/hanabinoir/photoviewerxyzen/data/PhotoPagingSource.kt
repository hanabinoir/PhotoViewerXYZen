package xyz.hanabinoir.photoviewerxyzen.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.hanabinoir.photoviewerxyzen.network.PhotoService

class PhotoPagingSource(
    private val service: PhotoService,
    private val query: String
): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1

        return try {
            val photos = service.getPhotos(query, page).photos
            LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}
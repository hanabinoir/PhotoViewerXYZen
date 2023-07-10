package xyz.hanabinoir.photoviewerxyzen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.hanabinoir.photoviewerxyzen.data.PhotoSearch
import xyz.hanabinoir.photoviewerxyzen.network.PhotoRepository

class PhotoViewModel: ViewModel() {

    private val repo = PhotoRepository()

    private val _photos = MutableLiveData<PhotoSearch>(null)
    val photos: LiveData<PhotoSearch> = _photos

    init {
        fetchPhotos("people")
    }

    fun fetchPhotos(query: String? = null) {
        viewModelScope.launch {
            val photos = repo.getPhotos(query)
            _photos.value = photos
        }
    }
}
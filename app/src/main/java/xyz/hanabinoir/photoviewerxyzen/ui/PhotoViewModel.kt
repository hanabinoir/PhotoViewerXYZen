package xyz.hanabinoir.photoviewerxyzen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.hanabinoir.photoviewerxyzen.data.PhotoListModel
import xyz.hanabinoir.photoviewerxyzen.network.PhotoRepository

class PhotoViewModel: ViewModel() {

    private val repo = PhotoRepository()

    private val _photos = MutableLiveData<PhotoListModel>(null)
    val photos: LiveData<PhotoListModel> = _photos

    fun fetchPhotos(query: String) {
        viewModelScope.launch {
            val photos = repo.getPhotos(query)
            _photos.value = photos
        }
    }
}
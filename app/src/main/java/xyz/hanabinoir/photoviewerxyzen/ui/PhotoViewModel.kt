package xyz.hanabinoir.photoviewerxyzen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import xyz.hanabinoir.photoviewerxyzen.data.Photo
import xyz.hanabinoir.photoviewerxyzen.network.APIService
import xyz.hanabinoir.photoviewerxyzen.network.PhotoRepositoryImpl

class PhotoViewModel: ViewModel() {

    private val repo = PhotoRepositoryImpl(APIService.photoService)

    private val _photos = MutableLiveData<List<Photo>>(null)
    val photos: LiveData<List<Photo>> = _photos

    init {
        fetchPhotos()
    }

    fun fetchPhotos(query: String = "people") {
        viewModelScope.launch {
            repo.getPhotos(query)
                .flowOn(Dispatchers.IO)
                .catch { e -> println(e.localizedMessage) }
                .onEmpty { _photos.value = listOf() }
                .collect { _photos.value = it.photos }
        }
    }
}
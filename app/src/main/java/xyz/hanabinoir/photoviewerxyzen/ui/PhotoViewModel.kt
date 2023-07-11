package xyz.hanabinoir.photoviewerxyzen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import xyz.hanabinoir.photoviewerxyzen.data.PhotoPagingSource
import xyz.hanabinoir.photoviewerxyzen.network.APIService

class PhotoViewModel: ViewModel() {

    private val _search = MutableStateFlow("")
    private val search = _search.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagedPhotos = search.debounce(300).flatMapLatest { query ->
        Pager(
            PagingConfig(
                pageSize = 15
            )
        ) {
            PhotoPagingSource(
                APIService.photoService,
                query
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String? = null) {
        val q = query ?: search.value.ifEmpty { "people" }
        _search.value = q
    }
}
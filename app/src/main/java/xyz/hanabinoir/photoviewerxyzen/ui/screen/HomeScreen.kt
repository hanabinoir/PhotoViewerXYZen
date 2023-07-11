package xyz.hanabinoir.photoviewerxyzen.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import xyz.hanabinoir.photoviewerxyzen.R
import xyz.hanabinoir.photoviewerxyzen.data.Photo
import xyz.hanabinoir.photoviewerxyzen.ui.NavScreen
import xyz.hanabinoir.photoviewerxyzen.ui.PhotoViewModel
import xyz.hanabinoir.photoviewerxyzen.util.Common

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: PhotoViewModel = viewModel()
) {

    val photos = viewModel.pagedPhotos.collectAsLazyPagingItems()

    Column {
        SearchBar(
            onSearch = { raw ->
                val query = Common().optimizeKeywords(raw)
                query?.let {
                    viewModel.searchPhotos(it)
                }
            }
        )
        PhotoList(photos = photos, selectPhoto = { photo, alt ->
            val route = "${NavScreen.Detail.route}?selectedPhoto=$photo&description=$alt"
            navController.navigate(route)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    onSearch: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val clearAlpha = if (text.isEmpty()) 0f else 1.0f

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = null,
                modifier = Modifier
                    .alpha(clearAlpha)
                    .clickable {
                        text = ""
                    }
            )
        }
    )
}

@Composable
private fun PhotoList(
    photos: LazyPagingItems<Photo>,
    selectPhoto: (String, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                all = 16.dp
            )
    ) {
        if (photos.loadState.refresh == LoadState.Loading) {
            item {
                StatusBox(stringResource(id = R.string.status_loading))
            }
        }

        items(
            photos.itemCount

        ) { i ->
            photos[i]?.let {
                ListItem(item = it, selectPhoto)
            }
        }

        if (photos.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }

        }
    }
}

@Composable
private fun ListItem(
    item: Photo,
    selectPhoto: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = 8.dp
            )
            .clickable {
                val selected = item.src.original
                val desc = item.alt
                selectPhoto(selected, desc)
            }
    ) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = item.alt,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        AsyncImage(model = item.src.tiny, contentDescription = item.alt)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.photographer,
            fontWeight = FontWeight.Light,
            color = Color.LightGray,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun StatusBox(msg: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = msg,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
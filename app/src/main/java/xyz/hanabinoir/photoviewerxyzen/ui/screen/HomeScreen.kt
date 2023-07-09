package xyz.hanabinoir.photoviewerxyzen.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import xyz.hanabinoir.photoviewerxyzen.ui.PhotoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import xyz.hanabinoir.photoviewerxyzen.data.PhotoModel
import xyz.hanabinoir.photoviewerxyzen.ui.NavScreen

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: PhotoViewModel = viewModel()
) {
    val photos = viewModel.photos.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPhotos("people")
    }

    val items = photos.value?.photos ?: run {
        StatusBox()
        return
    }

    PhotoList(photos = items, navController)
}

@Composable
private fun PhotoList(
    photos: List<PhotoModel>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                all = 16.dp
            )
    ) {
        items(photos) { item: PhotoModel ->
            ListItem(
                item = item,
                navController = navController
            )
        }
    }
}

@Composable
private fun ListItem(
    item: PhotoModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(
                top = 8.dp
            )
            .clickable {
                val selected = item.src.original
                val desc = item.alt
                val dest = NavScreen.Detail.route
                navController.navigate("$dest?selectedPhoto=$selected&description=$desc")
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
private fun StatusBox() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading",
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
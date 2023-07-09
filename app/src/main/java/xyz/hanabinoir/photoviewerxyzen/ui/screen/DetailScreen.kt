package xyz.hanabinoir.photoviewerxyzen.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage


@Composable
fun DetailScreen(
    selectedPhoto: String? = null,
    description: String? = null
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SelectedPhoto(
            url = selectedPhoto,
            alt = description ?: "loading"
        )
    }
}

@Composable
fun SelectedPhoto(url: String?, alt: String) {
    SubcomposeAsyncImage(
        model = url,
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = alt,
    )
}

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen()
}



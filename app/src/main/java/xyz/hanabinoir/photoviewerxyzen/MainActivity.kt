package xyz.hanabinoir.photoviewerxyzen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.hanabinoir.photoviewerxyzen.ui.NavGraph
import xyz.hanabinoir.photoviewerxyzen.ui.theme.PhotoViewerXYZenTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoViewerXYZenTheme {
                // A surface container using the 'background' color from the theme
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}


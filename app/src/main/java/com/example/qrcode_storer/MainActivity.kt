package com.example.qrcode_storer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qrcode_storer.ui.MainScreen
import com.example.qrcode_storer.ui.favorites.FavoritesScreen
import com.example.qrcode_storer.ui.theme.QrcodestorerTheme

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val route: String,
    val badgeCount: Int? = null
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QrcodestorerTheme {
                val items = listOf(
                    BottomNavigationItem(
                        title = "Scanner",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        hasNews = false,
                        route = "scanner"
                    ),
                    BottomNavigationItem(
                        title = "Favorites",
                        selectedIcon = Icons.Filled.Favorite,
                        unselectedIcon = Icons.Outlined.Favorite,
                        hasNews = false,
                        route = "favorites"
                    ),
                )

                val navController = rememberNavController()
                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationRoute!!) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    label = {
                                        Text(text = item.title)
                                    },
                                    alwaysShowLabel = false,
                                    icon = {
                                        BadgedBox(
                                            badge = {
                                                if (item.badgeCount != null) {
                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if (item.hasNews) {
                                                    Badge()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { padding ->

                    NavHost(
                        navController = navController,
                        startDestination = "scanner",
                        modifier = Modifier.padding(padding)
                    ) {
                        composable("scanner") { MainScreen() }
                        composable("favorites") { FavoritesScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun AppTitle() {
    Text(
        text = "MLKit QRCode scanner",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QrcodestorerTheme {
        AppTitle()
    }
}
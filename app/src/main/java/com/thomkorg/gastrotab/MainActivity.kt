package com.thomkorg.gastrotab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thomkorg.gastrotab.data.BottomNavItem
import com.thomkorg.gastrotab.ui.theme.GastroTabTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val userId = intent?.getStringExtra("user_id")
        val emailId = intent?.getStringExtra("email_id")

        setContent {
            GastroTabTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = {
                    BottomNavigationBar(
                        items = listOf(
                            BottomNavItem("Tische", Screen.TischScreen.route, Icons.Default.LocalCafe, 0),
                            BottomNavItem("Drinks", Screen.DrinkScreen.route, Icons.Default.LocalDrink, 0),
                            BottomNavItem("Settings", Screen.SettingsScreen.route, Icons.Default.Settings, 0)
                        ),
                        navController = navController,
                        onItemClick = { navController.navigate(it.route) }
                    )
                }) {
                    Navigation(navController)
                }

            }
        }
    }

}

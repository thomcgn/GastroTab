package com.thomkorg.gastrotab

sealed class Screen(val route:String){
    object TischScreen : Screen("tische")
    object DrinkScreen : Screen("drinks")
    object SettingsScreen : Screen("settings")
}

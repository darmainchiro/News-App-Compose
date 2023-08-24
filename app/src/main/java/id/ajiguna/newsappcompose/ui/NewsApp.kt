package id.ajiguna.newsappcompose.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.ajiguna.newsappcompose.BottomMenuScreen
import id.ajiguna.newsappcompose.MockData
import id.ajiguna.newsappcompose.components.BottomMenu
import id.ajiguna.newsappcompose.ui.screen.Categories
import id.ajiguna.newsappcompose.ui.screen.DetailScreen
import id.ajiguna.newsappcompose.ui.screen.Sources
import id.ajiguna.newsappcompose.ui.screen.TopNews

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()

    MainScreen(navController = navController, scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState){
    Scaffold(
        bottomBar = {
            BottomMenu(navController = navController)
        },
        content = {it
            Navigation(navController = navController, scrollState = scrollState)
        }
    )
}

@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState){
    NavHost(navController = navController, startDestination = BottomMenuScreen.TopNews.route){

        bottomNavigation(navController = navController)
        composable("Detail/{newsId}",
            arguments = listOf(navArgument("newsId"){type = NavType.IntType})
        ){
            navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailScreen(newsData, scrollState, navController)
        }
    }

}

fun NavGraphBuilder.bottomNavigation(navController: NavController){
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController)
    }
    composable(BottomMenuScreen.Categories.route){
        Categories()
    }
    composable(BottomMenuScreen.Sources.route){
        Sources()
    }

}
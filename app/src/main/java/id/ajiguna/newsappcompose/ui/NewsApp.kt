package id.ajiguna.newsappcompose.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.ajiguna.newsappcompose.BottomMenuScreen
import id.ajiguna.newsappcompose.components.BottomMenu
import id.ajiguna.newsappcompose.network.models.TopNewsArticle
import id.ajiguna.newsappcompose.network.NewsManager
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
            Navigation(navController = navController, scrollState = scrollState, paddingValues = it )
        }
    )
}

@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState, newsManager: NewsManager = NewsManager(), paddingValues: PaddingValues){
    val articles = newsManager.newsResponse.value.articles
    Log.d("news", "$articles")

    articles?.let {
        NavHost(navController = navController, startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues)) {
            bottomNavigation(navController = navController, articles, newsManager)
            composable("Detail/{index}",
                arguments = listOf(navArgument("index"){type = NavType.IntType})){
                navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }

}

fun NavGraphBuilder.bottomNavigation(navController: NavController, article: List<TopNewsArticle>, newsManager: NewsManager){
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController, article = article)
    }
    composable(BottomMenuScreen.Categories.route){
        newsManager.getArticlesByCategory("business")
        newsManager.onSelectedCategoryChanged("business")

        Categories(newsManager = newsManager,
            onFetchCategory = {
                newsManager.onSelectedCategoryChanged(it)
                newsManager.getArticlesByCategory(it)
            })
    }
    composable(BottomMenuScreen.Sources.route){
        Sources()
    }

}
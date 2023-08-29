package id.ajiguna.newsappcompose.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import id.ajiguna.newsappcompose.network.ApiClient
import id.ajiguna.newsappcompose.network.models.TopNewsArticle
import id.ajiguna.newsappcompose.network.NewsManager
import id.ajiguna.newsappcompose.ui.screen.Categories
import id.ajiguna.newsappcompose.ui.screen.DetailScreen
import id.ajiguna.newsappcompose.ui.screen.Sources
import id.ajiguna.newsappcompose.ui.screen.TopNews
import retrofit2.http.Query

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()

    MainScreen(navController = navController, scrollState, mainViewModel)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState, mainViewModel: MainViewModel){
    Scaffold(
        bottomBar = {
            BottomMenu(navController = navController)
        },
        content = {it
            Navigation(navController = navController, scrollState = scrollState, paddingValues = it , viewModel = mainViewModel)
        }
    )
}

@Composable
fun Navigation(navController: NavHostController,
               scrollState: ScrollState,
               newsManager: NewsManager = NewsManager(ApiClient.retrofitService),
               paddingValues: PaddingValues,
               viewModel: MainViewModel){

    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()

    val articles =  mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?:
    listOf())
    Log.d("news", "$articles")

    articles?.let {
        NavHost(navController = navController, startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues)) {
            val queryState = mutableStateOf(viewModel.query.value)
            val isLoading = mutableStateOf(loading)
            val isError = mutableStateOf(error)

            bottomNavigation(navController = navController, articles,
                query = queryState, viewModel, isError = isError, isLoading = isLoading)
            composable("Detail/{index}",
                arguments = listOf(navArgument("index"){type = NavType.IntType})){
                navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    if (queryState.value != ""){
                        articles.clear()
                        articles.addAll(viewModel.searchedNewsResponse.value.articles ?: listOf())
                    } else {
                        articles.clear()
                        articles.addAll(viewModel.newsResponse.value.articles ?: listOf())
                    }
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }

}

fun NavGraphBuilder.bottomNavigation(navController: NavController, articles: List<TopNewsArticle>,
                                     query: MutableState<String>, viewModel: MainViewModel,
                                     isLoading: MutableState<Boolean>, isError: MutableState<Boolean>){
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController, articles = articles,
            query = query, viewModel = viewModel, isLoading = isLoading, isError = isError)
    }
    composable(BottomMenuScreen.Categories.route){
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")

        Categories(viewModel = viewModel,
            onFetchCategory = {
                viewModel.onSelectedCategoryChanged(it)
                viewModel.getArticlesByCategory(it)
            }, isError = isError, isLoading = isLoading)
    }
    composable(BottomMenuScreen.Sources.route){
        Sources(viewModel = viewModel, isLoading = isLoading, isError = isError)
    }

}
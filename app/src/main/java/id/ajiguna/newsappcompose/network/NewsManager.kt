package id.ajiguna.newsappcompose.network

import android.util.Log
import androidx.compose.runtime.*
import id.ajiguna.newsappcompose.model.ArticleCategory
import id.ajiguna.newsappcompose.model.getArticleCategory
import id.ajiguna.newsappcompose.network.models.TopNewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class NewsManager(private val service: NewsService) {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember{
            _newsResponse
        }

    private val _getArticleByCategory =
        mutableStateOf(TopNewsResponse())
    val getArticleByCategory:MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }


    val sourceName = mutableStateOf("abc-news")
    private val _getArticleBySource =
        mutableStateOf(TopNewsResponse())

    val getArticleBySource:MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    val query = mutableStateOf("")

    private val _searchedNewsResponse =
        mutableStateOf(TopNewsResponse())
    val searchedNewsResponse:State<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }

    suspend fun getArticles(country: String): TopNewsResponse
    = withContext(Dispatchers.IO){
        service.getTopArticle(country)
    }

    suspend fun getArticlesByCategory(category: String): TopNewsResponse
            = withContext(Dispatchers.IO){
        service.getArticlesByCategories(category)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }

    suspend fun getArticleBySource(source: String): TopNewsResponse
    = withContext(Dispatchers.IO){
        service.getArticlesBySources(source)
    }

    suspend fun getSearchedArticles(query: String): TopNewsResponse
            = withContext(Dispatchers.IO){
        service.getArticles(query)
    }

}
package id.ajiguna.newsappcompose.network

import android.util.Log
import androidx.compose.runtime.*
import id.ajiguna.newsappcompose.model.ArticleCategory
import id.ajiguna.newsappcompose.model.getArticleCategory
import id.ajiguna.newsappcompose.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class NewsManager {

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
    init {
        getArticles()
    }

    val query = mutableStateOf("")

    private val _searchedNewsResponse =
        mutableStateOf(TopNewsResponse())
    val searchedNewsResponse:State<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }

    private fun getArticles(){
        val service = ApiClient.retrofitService.getTopArticle("us")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _newsResponse.value = response.body()!!
                    Log.d("news","${_newsResponse.value}")
                }else{
                    Log.d("error","${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error","${t.printStackTrace()}")
            }

        })
    }

    fun getArticlesByCategory(category: String){
        val client = ApiClient.retrofitService.getArticlesByCategories(category)
        client.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _getArticleByCategory.value = response.body()!!
                    Log.d("category","${_getArticleByCategory.value}")
                }else{
                    Log.d("error","${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("category_error","${t.printStackTrace()}")
            }

        })
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }

    fun getArticleBySource(){
        val client = ApiClient.retrofitService.getArticlesBySources(sourceName.value)
        client.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful) {
                    _getArticleBySource.value = response.body()!!
                    Log.d("source", "${_getArticleBySource.value}")
                } else {
                    Log.d("search","${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {

            }

        })
    }

    fun getSearchedArticles(query: String){
        val client = ApiClient.retrofitService.getArticles(query)
        client.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _searchedNewsResponse.value = response.body()!!
                    Log.d("search","${_searchedNewsResponse.value}")
                } else {
                    Log.d("search","${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("search_error","${t.printStackTrace()}")

            }

        })
    }

}
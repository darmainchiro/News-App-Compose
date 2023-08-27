package id.ajiguna.newsappcompose.network

import android.util.Log
import androidx.compose.runtime.*
import id.ajiguna.newsappcompose.model.ArticleCategory
import id.ajiguna.newsappcompose.model.getArticleCategory
import id.ajiguna.newsappcompose.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)
    init {
        getArticles()
    }

    private fun getArticles(){
        val service = ApiClient.retrofitService.getTopArticle("us",ApiClient.API_KEY)
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
        val client = ApiClient.retrofitService.getArticlesByCategories(category,ApiClient.API_KEY)
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
                Log.d("error","${t.printStackTrace()}")
            }

        })
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }

}
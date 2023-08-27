package id.ajiguna.newsappcompose.network

import id.ajiguna.newsappcompose.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    fun getTopArticle(
        @Query("country")country: String
    ): Call<TopNewsResponse>

    @GET("top-headlines")
    fun getArticlesByCategories(
        @Query("category") category:String
    ):Call<TopNewsResponse>

    @GET("everything")
    fun getArticlesBySources(@Query("sources") source:String
    ):Call<TopNewsResponse>

    @GET("everything")
    fun getArticles(@Query("q") source:String
    ):Call<TopNewsResponse>

}
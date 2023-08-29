package id.ajiguna.newsappcompose.network

import id.ajiguna.newsappcompose.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun getTopArticle(
        @Query("country")country: String
    ): TopNewsResponse

    @GET("top-headlines")
    suspend fun getArticlesByCategories(
        @Query("category") category:String
    ): TopNewsResponse

    @GET("everything")
    suspend fun getArticlesBySources(@Query("sources") source:String
    ): TopNewsResponse

    @GET("everything")
    suspend fun getArticles(@Query("q") source:String
    ): TopNewsResponse

}
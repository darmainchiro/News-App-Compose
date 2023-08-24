package id.ajiguna.newsappcompose.network

import id.ajiguna.newsappcompose.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    fun getTopArticle(
        @Query("country")country: String,
        @Query("apiKey") apiKey: String
    ): Call<TopNewsResponse>
}
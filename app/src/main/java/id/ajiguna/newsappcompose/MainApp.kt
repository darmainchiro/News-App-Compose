package id.ajiguna.newsappcompose

import android.app.Application
import id.ajiguna.newsappcompose.data.Repository
import id.ajiguna.newsappcompose.network.ApiClient
import id.ajiguna.newsappcompose.network.NewsManager

class MainApp: Application() {
    private val manager by lazy {
        NewsManager(ApiClient.retrofitService)
    }

    val repository by lazy {
        Repository(manager)
    }
}
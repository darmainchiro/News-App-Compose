package id.ajiguna.newsappcompose.data

import id.ajiguna.newsappcompose.network.NewsManager

class Repository(val  manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("us")

    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)

    suspend fun getArticleBySource(source: String)
    = manager.getArticleBySource(source)

    suspend fun getSearchedArticles(query: String)
            = manager.getSearchedArticles(query)
}
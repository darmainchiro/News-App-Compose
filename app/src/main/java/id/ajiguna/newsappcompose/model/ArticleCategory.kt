package id.ajiguna.newsappcompose.model

import id.ajiguna.newsappcompose.model.ArticleCategory.*

enum class ArticleCategory(val categoryName: String){
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}

fun getAllArticleCategory():List<ArticleCategory>{
    return listOf(BUSINESS, ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY)
}

fun getArticleCategory(category: String): ArticleCategory?{
    val map = values().associateBy(ArticleCategory::categoryName)
    return map[category]
}
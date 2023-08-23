package id.ajiguna.newsappcompose

data class NewsData(
    val id: Int,
    val image: Int = R.drawable.example,
    val author: String,
    val title: String,
    val description: String,
    val publishedAt: String
)

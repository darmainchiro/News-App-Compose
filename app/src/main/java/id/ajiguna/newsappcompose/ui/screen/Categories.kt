package id.ajiguna.newsappcompose.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.ajiguna.newsappcompose.R
import com.skydoves.landscapist.coil.CoilImage
import id.ajiguna.newsappcompose.components.ErrorUI
import id.ajiguna.newsappcompose.components.LoadingUI
import id.ajiguna.newsappcompose.model.getAllArticleCategory
import id.ajiguna.newsappcompose.model.MockData
import id.ajiguna.newsappcompose.model.MockData.getTimeAgo
import id.ajiguna.newsappcompose.network.NewsManager
import id.ajiguna.newsappcompose.network.models.TopNewsArticle
import id.ajiguna.newsappcompose.ui.MainViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Categories(onFetchCategory: (String) -> Unit={}, viewModel: MainViewModel,
               isLoading: MutableState<Boolean>, isError: MutableState<Boolean>){

    val tabsItems = getAllArticleCategory()
    Column {

        when{
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            } else -> {
                LazyRow(){
                    items(tabsItems.size){
                        val category = tabsItems[it]
                        CategoryTab(
                            category = category.categoryName, onFetchCategory = onFetchCategory,
                            isSelected =
                            viewModel.selectedCategory.collectAsState().value == category
                        )
                    }
                }
            }
        }

        ArticleContent(articles = viewModel.getArticleByCategory.collectAsState().value.articles ?: listOf())
    }
}

@Composable
fun CategoryTab(category: String,
                isSelected: Boolean = false,
                onFetchCategory: (String) -> Unit){
    val background = if (isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
        )

    }
}


@Composable
fun ArticleContent(articles: List<TopNewsArticle>, modifier: Modifier = Modifier){
    LazyColumn{
        items(articles){article->
            Card(modifier.padding(8.dp), border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_500))) {
                Row (
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ){
                    CoilImage(imageModel = article.urlToImage,modifier = Modifier.size(100.dp),placeHolder = painterResource(
                        id = R.drawable.example),error = painterResource(
                        id = R.drawable.example) )
                    Column(modifier.padding(8.dp) ) {
                            Text(text = article.title ?: "Not Available", fontWeight = FontWeight.Bold,
                            maxLines = 3,overflow = TextOverflow.Ellipsis)
                        Row(modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = article.author?:"Not Available")
                            Text(text = MockData.stringToDate(article.publishedAt?:"2021-11-10T14:25:20Z").getTimeAgo())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ArticleContentPriview(){
    ArticleContent(
        articles = listOf(TopNewsArticle(
            author = "CBSBoston.com Staff",
            title = "Principal Beaten Unconscious At Dorchester School; Classes Canceled Thursday - CBS Boston",
            description = "Principal Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
            publishedAt = "2021-11-04T01:55:00Z"
        ))
    )
}
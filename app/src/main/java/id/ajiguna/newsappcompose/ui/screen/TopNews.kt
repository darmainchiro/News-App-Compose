package id.ajiguna.newsappcompose.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import id.ajiguna.newsappcompose.model.MockData
import id.ajiguna.newsappcompose.model.MockData.getTimeAgo
import id.ajiguna.newsappcompose.R
import id.ajiguna.newsappcompose.network.models.TopNewsArticle

@Composable
fun TopNews(navController: NavController, article: List<TopNewsArticle>) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Top News", fontWeight =  FontWeight.SemiBold)
        LazyColumn{
            items(article.size){
                index ->
                TopNewsItem(article = article[index],
                    onNewsClick = {  navController.navigate("Detail/$index")}
                )
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClick: ()-> Unit = {}){
    Box (modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }){
        CoilImage(
            imageModel = article.urlToImage,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(R.drawable.example),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(R.drawable.example)
        )
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = MockData.stringToDate(article.publishedAt!!).getTimeAgo(), color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = article.title!!, color = Color.White, fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview(){
    TopNewsItem(
        TopNewsArticle(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        )
    )
}
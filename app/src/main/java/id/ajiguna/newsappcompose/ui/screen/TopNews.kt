package id.ajiguna.newsappcompose.ui.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.ajiguna.newsappcompose.MockData
import id.ajiguna.newsappcompose.MockData.getTimeAgo
import id.ajiguna.newsappcompose.NewsData

@Composable
fun TopNews(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Top News", fontWeight =  FontWeight.SemiBold)
        LazyColumn{
            items(MockData.topNewsList){
                newsData ->
                TopNewsItem(newsData = newsData, onNewsClick = {
                    navController.navigate("Detail/${newsData.id}")
                })
            }
        }
    }
}

@Composable
fun TopNewsItem(newsData: NewsData, onNewsClick: ()-> Unit = {}){
    Box (modifier = Modifier
        .height(200.dp)
        .padding(8.dp).clickable {
            onNewsClick()
        }){
        Image(painter = painterResource(id = newsData.image), contentDescription = "",
            contentScale = ContentScale.FillBounds)
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = MockData.stringToDate(newsData.publishedAt).getTimeAgo(), color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = newsData.title, color = Color.White, fontWeight = FontWeight.SemiBold)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview(){
    TopNews(rememberNavController())
}
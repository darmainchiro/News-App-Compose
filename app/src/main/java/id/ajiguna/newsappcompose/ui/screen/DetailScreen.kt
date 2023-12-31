package id.ajiguna.newsappcompose.ui.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.coil.CoilImage
import id.ajiguna.newsappcompose.model.MockData
import id.ajiguna.newsappcompose.model.MockData.getTimeAgo
import id.ajiguna.newsappcompose.R
import id.ajiguna.newsappcompose.network.models.TopNewsArticle

@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState, navController: NavController){
    Scaffold(
        topBar = {
            DetailTopAppBar(onBackPressed = {navController.popBackStack()})
        },
        content = {it
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoilImage(
                    imageModel = article.urlToImage,
                    // Crop, Fit, Inside, FillHeight, FillWidth, None
                    contentScale = ContentScale.Crop,
                    error = ImageBitmap.imageResource(R.drawable.example),
                    // shows a placeholder ImageBitmap when loading.
                    placeHolder = ImageBitmap.imageResource(R.drawable.example)
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoWithIcon(Icons.Default.Edit, info = article.author?:"Not Available")
                    InfoWithIcon(Icons.Default.DateRange, info = MockData.stringToDate(article.publishedAt!!).getTimeAgo())
                }
                Text(text = article.title?:"Not Available", fontWeight = FontWeight.Bold)
                Text(text = article.description?:"Not Available", modifier = Modifier.padding(top=16.dp))
            }
        }
    )
}

@Composable
fun DetailTopAppBar(onBackPressed: ()-> Unit = {}){
    TopAppBar(title = {
      Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)},
        navigationIcon = {
            IconButton(onClick = { onBackPressed }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        }
    )
}

@Composable
fun InfoWithIcon( icon: ImageVector, info: String){
    Row {
        Icon(icon, contentDescription = "Author",
            modifier = Modifier.padding(end=8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(){
    DetailScreen(
        TopNewsArticle(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ),
        rememberScrollState(),
        rememberNavController()
    )
}
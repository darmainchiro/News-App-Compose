package id.ajiguna.newsappcompose.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ajiguna.newsappcompose.network.ApiClient
import id.ajiguna.newsappcompose.network.NewsManager
import id.ajiguna.newsappcompose.ui.MainViewModel

@Composable
fun SearchBar(query: MutableState<String>, viewModel: MainViewModel){
    var text by remember { mutableStateOf("Hello") }

    val localFocusManager = LocalFocusManager.current
    Card(elevation = 6.dp, shape = RoundedCornerShape(4.dp), modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        backgroundColor = MaterialTheme.colors.primary){
        TextField(value = query.value, onValueChange = {
                query.value = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Search",color = White)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "", tint = White)
            },
            trailingIcon = {
                if (query.value != "") {
                    IconButton(
                        onClick = {
                            query.value = ""
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = White)
                    }
                }
            },
            textStyle = TextStyle(color = White, fontSize = 18.sp),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.value != ""){
                        viewModel.getSearchedArticles(query.value)
                    }
                    localFocusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(textColor = White)
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun SearchBarPreview(){
    SearchBar(query = mutableStateOf(""), viewModel() )
}
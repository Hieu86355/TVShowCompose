package com.example.tvshow.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tvshow.R
import com.example.tvshow.ui.LoadAnimation
import com.example.tvshow.ui.theme.Poppins

@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    navController: NavController,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        SearchBar(navController = navController)
        SearchResultList(navController = navController)
    }

}

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )
        SearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            onSearchSubmit = {
                if (it.isNotEmpty()) {
                    searchViewModel.query = it
                    searchViewModel.clearSearchResult()
                    searchViewModel.getSearch()
                }
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearchSubmit: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    )
    {
        BasicTextField(value = searchText,
            onValueChange = { searchText = it },
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchSubmit(searchText)
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                }
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                autoCorrect = false
            ),
            textStyle = TextStyle(
                color = Color.DarkGray,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp)
                .focusRequester(focusRequester)
        )

        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    searchText = ""
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
        )
    }
}

@Composable
fun SearchResultList(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val searchResults by remember { searchViewModel.searchResults }
    val isLoading by remember { searchViewModel.isLoading }
    val isLastPage by remember { searchViewModel.isLastPage }
    val error by remember { searchViewModel.error }

    LazyColumn(modifier = Modifier.padding(16.dp)) {

        if (error.isNotEmpty()) {
            item {
                Text(
                    text = error,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                )
            }
        }


        itemsIndexed(searchResults) { index, show ->
            if (index >= searchResults.size - 1 && !isLoading && !isLastPage) {
                LaunchedEffect(true) {
                    searchViewModel.getSearch()
                }
            }
            SearchItem(
                show = show,
                imageLoader = searchViewModel.imageLoader,
                navController = navController
            )
        }

        if (isLoading) {
            item {
                LoadAnimation(
                    resource = R.raw.loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(vertical = 16.dp),
                    speed = 2.5f
                )
            }
        }
    }

}
package com.pratik.loginmodule.cleanArch.screens

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pratik.loginmodule.UIState
import com.pratik.loginmodule.cleanArch.dao.WishListDTO
import com.pratik.loginmodule.cleanArch.dao.ui.theme.LoginModuleTheme
import com.pratik.loginmodule.cleanArch.viewmodels.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListCompose : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginModuleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(name = "")

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, wishlistViewModel: WishlistViewModel = hiltViewModel()) {
    val wishListDTO = WishListDTO(
        availabilityStatus = "status",
        brand = "brand",
        category = "category",
        price = 33.44,
        discountPercentage = 45.66,
        rating = 3.5,
        title = "title", description = "description"
    )
    LaunchedEffect(Unit) {
        wishlistViewModel.getAllWishlist()

    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState by wishlistViewModel.wishListStateFlow.collectAsState()
        when (uiState) {
            is UIState.Loading -> {
                //  CircularProgressIndicator()

            }

            is UIState.Success -> {
                var result = (uiState as UIState.Success<List<WishListDTO>>).data
                LazyColumn(contentPadding = PaddingValues(8.dp)) {
                    items(result) { item ->
                        WishListCard(
                            product = item,
                            productImageRes = R.drawable.ic_menu_search,
                            onAddToBagClick = { /*TODO*/ },
                            onDeleteClick = {
                                wishlistViewModel.deleteWishList(item.id)
                            },
                            onItemClick = { /*TODO*/ })
                    }

                }
            }

            is UIState.Error -> {
                Text(
                    "Error: ${(uiState as UIState.Error).message}",
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
    }


}


@Composable
fun NumericUpDown(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    step: Int = 1
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .wrapContentWidth()
            .padding(8.dp)
    ) {
        IconButton(
            onClick = {
                if (value + step <= maxValue) {
                    onValueChange(value + step)
                }
            },
            enabled = value < maxValue,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Increase")
        }
        BasicTextField(
            value = value.toString(),
            onValueChange = { newValue ->
                newValue.toIntOrNull()?.let {
                    if (it in minValue..maxValue) {
                        onValueChange(it)
                    }
                }
            },
            modifier = Modifier
                .width(60.dp)
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center)
        )
        IconButton(
            onClick = {
                if (value - step >= minValue) {
                    onValueChange(value - step)
                }
            },
            enabled = value > minValue,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Decrease")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    val wishListDTO = WishListDTO(
        availabilityStatus = "status",
        brand = "brand",
        category = "category",
        price = 33.44,
        discountPercentage = 45.66,
        rating = 3.5,
        title = "title", description = "description"
    )
    LoginModuleTheme {
        CartCard(
            product = wishListDTO,
            productImageRes = R.drawable.ic_menu_search,
            onDeleteClick = {

            }, onItemClick = {

            }
        )
    }
}
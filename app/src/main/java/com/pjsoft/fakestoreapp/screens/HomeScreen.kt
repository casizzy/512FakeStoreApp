package com.pjsoft.fakestoreapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.pjsoft.fakestoreapp.models.Product
import com.pjsoft.fakestoreapp.services.ProductService
import com.pjsoft.fakestoreapp.ui.theme.FakeStoreAppTheme
import com.pjsoft.fakestoreapp.ui.theme.ProductDetailScreenRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(navController: NavController) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ProductService::class.java)
            val result = withContext(Dispatchers.IO) { service.getAllProducts() }
            products = result
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        bottomBar = { CustomBottomNavigationBar() },
        containerColor = Color.Black
    ) { padding ->

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color.Black)
                ) {
                    Spacer(
                        modifier = Modifier
                            .background(Color.Black)
                            .windowInsetsTopHeight(WindowInsets.statusBars)
                            .align(Alignment.TopCenter)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .clip(RoundedCornerShape(topStart = 80.dp))
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White)
                    )
                    Text(
                        text = "Los mejores productos\nal mejor precio.",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 26.sp,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 40.dp)
                            .padding(horizontal = 12.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 16.dp)
                        .offset(y = (-30).dp)
                ) {
                    Text("Productos", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Spacer(Modifier.height(20.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(products) { product ->
                            ProductCard(product) {
                                navController.navigate(ProductDetailScreenRoute(product.id))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(red = 227, green = 227, blue = 227)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(product.title, maxLines = 1, fontWeight = FontWeight.SemiBold)
                Text(
                    "$${product.price}",
                    color = Color(0xFFFFAF07),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CustomBottomNavigationBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Home, contentDescription = null, tint = Color.White)
            Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(40.dp))
            Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp)
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFF2F2F2F)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.ShoppingBasket,
                contentDescription = "Lock",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    FakeStoreAppTheme {
        HomeScreen(rememberNavController())
    }
}